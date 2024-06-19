package org.team.bookshop.domain.product.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.team.bookshop.domain.category.entity.BookCategory;
import org.team.bookshop.domain.category.entity.BookCategoryId;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.repository.BookCategoryRepository;
import org.team.bookshop.domain.category.repository.CategoryRepository;
import org.team.bookshop.domain.product.dto.ProductDto;
import org.team.bookshop.domain.product.dto.ProductResponseDto;
import org.team.bookshop.domain.product.dto.ProductResponseMainDto;
import org.team.bookshop.domain.product.dto.ProductSaveRequestDto;
import org.team.bookshop.domain.product.dto.SimpleProductResponseDto;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.repository.ProductRepository;
import org.team.bookshop.global.error.exception.EntityNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;

    // CREATE & UPDATE
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SimpleProductResponseDto saveOrUpdateProduct(ProductSaveRequestDto request,
        MultipartFile pictureFile) throws IOException {

        Product product;
        // 업데이트 로직
        if (request.getId() != null) {
            product = productRepository.findById(request.getId())
                .orElseThrow(
                    () -> new EntityNotFoundException("Product not found: " + request.getId()));
            product.update(
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn(),
                request.getContent(),
                request.getFixedPrice(),
                request.getPublicationYear(),
                request.getStatus(),
                request.isDiscount()
            );
        } else {
            product = request.toEntity();
        }

        if (pictureFile != null && !pictureFile.isEmpty()) {
            String pictureUrl = savePictureFile(pictureFile);
            product.setPictureUrl(pictureUrl);
        }

        // 1. 상품 생성
        product = productRepository.save(product);

        // 2. 카테고리 ID 리스트에서 중복 제거
        Set<Long> uniqueCategoryIds = new HashSet<>(request.getCategoryIds());

        // 3. 자식 카테고리 ID 추출 (입력값 중 부모-자식 관계인 id가 있을 경우 부모 id를 제외)
        Set<Long> childCategoryIds = filterChildCategoryIds(uniqueCategoryIds);

        // 4. BookCategory 매핑 (엔티티 매핑)
        List<Category> categories = categoryRepository.findAllById(childCategoryIds);
        clearBookCategories(product); // 기존 BookCategory 엔티티 삭제
        for (Category category : categories) {
            BookCategory bookCategory = new BookCategory(
                new BookCategoryId(product.getId(), category.getId()), product, category
            );
            bookCategoryRepository.save(bookCategory); // BookCategory 엔티티 저장
            product.addBookCategory(bookCategory); // Product 엔티티에 추가
        }
        productRepository.save(product);

        return new SimpleProductResponseDto(product);
    }

    private String savePictureFile(MultipartFile pictureFile) throws IOException {
        if (pictureFile == null || pictureFile.isEmpty()) {
            return null;
        }

        Path directory = Paths.get(uploadDir);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        String originalFileName = pictureFile.getOriginalFilename();
        assert originalFileName != null;
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuidFileName = UUID.randomUUID() + fileExtension;
        Path filePath = directory.resolve(uuidFileName);

        Files.write(filePath, pictureFile.getBytes());

        return uuidFileName;
    }

    // 부모-자식 관계 필터링 메서드 (자식 카테고리 ID만 남김)
    private Set<Long> filterChildCategoryIds(Set<Long> categoryIds) {
        Set<Long> parentCategoryIds = new HashSet<>();
        for (Long categoryId : categoryIds) {
            List<Object[]> categoryPath = categoryRepository.findCategoryPath(categoryId);

            // 마지막 요소 제외하고 부모 카테고리 ID 추출
            for (int i = 0; i < categoryPath.size() - 1; i++) {
                Long parentId =
                    (categoryPath.get(i)[2] != null) ? ((Number) categoryPath.get(i)[2]).longValue()
                        : null;
                if (parentId != null) {
                    parentCategoryIds.add(parentId);
                }
            }
        }

        // 자식 카테고리 ID 필터링
        return categoryIds.stream()
            .filter(categoryId -> !parentCategoryIds.contains(categoryId))
            .collect(Collectors.toSet());
    }

    private void clearBookCategories(Product product) {
        Set<BookCategory> bookCategories = product.getBookCategories();
        for (BookCategory bookCategory : bookCategories) {
            bookCategory.setProduct(null);
            bookCategory.setCategory(null);
            bookCategoryRepository.delete(bookCategory);
        }
        bookCategories.clear();
    }

    // READ
    // 모든 상품 조회
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductResponseDto::new)
            .collect(Collectors.toList());
    }

    // 특정 상품 조회
    public ProductResponseDto getProduct(long id) {
        return productRepository.findById(id)
            .map(ProductResponseDto::new)
            .orElseThrow(() -> new EntityNotFoundException("not found: " + id));
    }

    // 카테고리별 상품 조회 paging, querydsl
    public Page<ProductDto> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        if (pageSize <= 0 || pageSize > 100) { // 페이지 크기 범위 제한 (예: 1~100)
            pageSize = 12; // 기본 페이지 크기 설정
            pageable = PageRequest.of(pageable.getPageNumber(), pageSize, pageable.getSort());
        }
        return productRepository.findByCategoryIds(categoryId, pageable)
            .map(ProductDto::new);
    }

    // 카테고리별 상품 조회
    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryIds(categoryId);
        return products.stream()
            .map(ProductDto::new)
            .collect(Collectors.toList());
    }

    // DELETE
    @Transactional
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found: " + id));
        productRepository.delete(product);
    }

    @Transactional
    public List<ProductResponseMainDto> getMainProducts() {
        return productRepository.findRandomProducts(24).stream()
            .map(product -> new ProductResponseMainDto(
                product.getId(),
                product.getTitle(),
                product.getAuthor(),
                product.getPictureUrl()
            ))
            .collect(Collectors.toList());
    }
}

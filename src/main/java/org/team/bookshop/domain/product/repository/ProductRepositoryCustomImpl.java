package org.team.bookshop.domain.product.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.category.entity.QBookCategory;
import org.team.bookshop.domain.category.entity.QCategory;
import org.team.bookshop.domain.category.repository.CategoryRepository;
import org.team.bookshop.domain.product.dto.ProductDto;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.entity.QProduct;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<ProductDto> findByCategoryIds(Long categoryId, Pageable pageable) {
        QProduct product = QProduct.product;
        QBookCategory bookCategory = QBookCategory.bookCategory;
        QCategory category = QCategory.category;

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        // 1. 재귀 쿼리를 통해 하위 카테고리 ID 목록 가져오기
        List<Object[]> categoryHierarchy = categoryRepository.findByIdWithChildren(categoryId);
        List<Long> categoryIds = categoryHierarchy.stream()
            .map(data -> ((Number) data[0]).longValue())
            .collect(Collectors.toList());

        // 2. 하위 카테고리 ID 목록을 포함하는 조건 생성
        BooleanExpression isChildCategory = category.id.in(categoryIds);

        // 3. 동적 정렬 적용
        OrderSpecifier<?> orderSpecifier = pageable.getSort().stream()
            .map(order -> {
                if (order.getProperty().equals("createdAt")) {
                    return new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC,
                        product.createdAt);
                } else if (order.getProperty().equals("fixedPrice")) {
                    return new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC,
                        product.fixedPrice);
                } else {
                    return null; // or throw an exception
                }
            })
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(new OrderSpecifier<>(Order.DESC, product.createdAt));

        // 4. 페이징 쿼리 및 전체 개수 조회 쿼리 (개선된 조건 적용)
        JPAQuery<Product> countQuery = queryFactory
            .selectFrom(product)
            .join(product.bookCategories, bookCategory).fetchJoin()
            .join(bookCategory.category, category).fetchJoin()
            .where(isChildCategory);

        JPAQuery<Product> contentQuery = queryFactory
            .selectFrom(product)
            .join(product.bookCategories, bookCategory).fetchJoin()
            .join(bookCategory.category, category).fetchJoin()
            .where(isChildCategory)
            .distinct()
            .orderBy(orderSpecifier)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        // 페이징 쿼리 실행 및 결과 가져오기
        List<Product> content = contentQuery.fetch();
        List<ProductDto> productDtoList = content.stream().map(ProductDto::new)
            .collect(Collectors.toList());

        // PageImpl 생성 및 반환
        return new PageImpl<>(productDtoList, pageable, countQuery.fetchCount());
    }

    @Override
    public List<Product> findByCategoryIds(Long categoryId) {
        QProduct product = QProduct.product;
        QBookCategory bookCategory = QBookCategory.bookCategory;
        QCategory category = QCategory.category;

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        // 카테고리 계층 쿼리 (기존 쿼리와 동일)
        BooleanExpression isChildCategory = category.id.eq(categoryId);
        isChildCategory = isChildCategory.or(category.parent.id.eq(categoryId));

        return queryFactory
            .selectFrom(product)
            .join(product.bookCategories, bookCategory)
            .join(bookCategory.category, category)
            .where(isChildCategory)
            .distinct()
            .fetch();
    }
}

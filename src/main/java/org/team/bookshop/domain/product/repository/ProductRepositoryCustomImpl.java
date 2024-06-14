package org.team.bookshop.domain.product.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.category.entity.QBookCategory;
import org.team.bookshop.domain.category.entity.QCategory;
import org.team.bookshop.domain.product.dto.ProductDto;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.entity.QProduct;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;

  @Override
  public Page<ProductDto> findByCategoryIds(Long categoryId, Pageable pageable) {
    QProduct product = QProduct.product;
    QBookCategory bookCategory = QBookCategory.bookCategory;
    QCategory category = QCategory.category;

    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

    // 카테고리 계층 쿼리 (기존 쿼리와 동일)
    BooleanExpression isChildCategory = category.id.eq(categoryId);
    isChildCategory = isChildCategory.or(category.parent.id.eq(categoryId));

    // 동적 정렬 적용
    OrderSpecifier<?> orderSpecifier = pageable.getSort().stream()
        .filter(order -> order.getProperty().equals("createdAt")) // 정렬 가능한 컬럼 제한
        .findFirst()
        .map(order -> new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC,
            product.createdAt))
        .orElse(new OrderSpecifier<>(Order.DESC, product.createdAt)); // 기본 정렬 설정

    // 페이징 쿼리 및 전체 개수 조회 쿼리
    JPAQuery<Product> countQuery = queryFactory
        .selectFrom(product)
        .join(product.bookCategories, bookCategory)
        .join(bookCategory.category, category)
        .where(isChildCategory);
    JPAQuery<Product> contentQuery = queryFactory
        .selectFrom(product)
        .join(product.bookCategories, bookCategory)
        .join(bookCategory.category, category)
        .where(isChildCategory)
        .distinct()
        .orderBy(orderSpecifier) // 동적 정렬 적용
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    // 페이징 쿼리 실행 및 결과 가져오기
    List<Product> content = contentQuery.fetch();
    List<ProductDto> productDtoList = content.stream().map(ProductDto::new)
        .toList(); // ProductDto 변환 추가

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

  @Override
  public Optional<Product> findByIdWithEntityGraph(Long id, Map<String, Object> hints) {
    return Optional.ofNullable(entityManager.find(Product.class, id, hints));
  }
}

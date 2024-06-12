package org.team.bookshop.domain.category.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.product.entity.Product;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByParentId(Long id);

    @Query("SELECT DISTINCT p FROM Product p " +
        "JOIN FETCH p.bookCategories bc " +
        "JOIN FETCH bc.category c " +
        "WHERE c.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.id = :parentId")
    Optional<Category> findByIdWithChildren(@Param("parentId") Long parentId);

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findRootCategory();

    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId")
    List<Category> findByParentId(@Param("parentId") Long parentId);

    @Query("SELECT c FROM Category c WHERE c.id = :categoryId OR c.parent.id = :categoryId OR c.parent.parent.id = :categoryId")
    List<Category> findAllSubcategories(@Param("categoryId") Long categoryId);
}

package org.team.bookshop.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.category.entity.BookCategory;
import org.team.bookshop.domain.category.entity.BookCategoryId;

public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategoryId> {

}

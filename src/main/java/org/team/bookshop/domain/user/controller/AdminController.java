package org.team.bookshop.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

//    private final ProductService productService;
//    private final UserService userService;
//    private final CategoryService categoryService;

//
//    @PostMapping("/categories")
//    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
//        Category newCategory = categoryService.addCategory(category);
//        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
//    }
//
//    .
}
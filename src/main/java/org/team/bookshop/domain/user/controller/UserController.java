package org.team.bookshop.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@RestController
@Slf4j
@Tag(name = "주문")
@RequestMapping("/api/v1/order")
public class UserController {


    //localhost:8080/order
    @GetMapping()
    @Operation(summary = "주문을 확인합니다.", description = "유저가 주문을 찾습니다.")
    public ResponseEntity<String> returnUrl() {
        try {
            throw new ApiException(ErrorCode.INVALID_INPUT_VALUE);
        } catch (ApiException e) {
            log.error("ErrorMessage: {} \n ", e.getMessage(), e);
        }
        return ResponseEntity.ok().build();
    }
}

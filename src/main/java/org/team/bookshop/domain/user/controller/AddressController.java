package org.team.bookshop.domain.user.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.user.dto.AddressPostDto;
import org.team.bookshop.domain.user.service.AddressService;

@RestController
@Tag(name = "주소")
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/address/{id}")
    public ResponseEntity<List<AddressPostDto>> getUserAddress(@PathVariable Long id) {
        List<AddressPostDto> addressPostDtoList = addressService.getUserAddress(id);
        return ResponseEntity.ok(addressPostDtoList);
    }

    @PostMapping("/address/{id}")
    public ResponseEntity<Void> saveUserAddress(@PathVariable Long id,
        @RequestBody @Valid AddressPostDto addressPostDto) {
        addressService.saveUserAddress(id, addressPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<AddressPostDto> updateUserAddress(@PathVariable Long id,
        @RequestBody @Valid AddressPostDto addressPostDto) {
        return ResponseEntity.ok(addressService.updateUserAddress(id, addressPostDto));
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable Long id) {
        addressService.deleteUserAddress(id);
        return ResponseEntity.noContent().build();
    }


}

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

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<AddressPostDto>> getUserAddresses(@PathVariable Long userId) {
        List<AddressPostDto> addressPostDtoList = addressService.getUserAddress(userId);
        return ResponseEntity.ok(addressPostDtoList);
    }

    @GetMapping("/{userId}/address/{addressId}")
    public ResponseEntity<AddressPostDto> getUserAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        AddressPostDto addressPostDto = addressService.getUserAddress(userId, addressId);
        return ResponseEntity.ok(addressPostDto);
    }

    @PostMapping("/{userId}/address")
    public ResponseEntity<Void> saveUserAddress(@PathVariable Long userId,
        @RequestBody @Valid AddressPostDto addressPostDto) {
        addressService.saveUserAddress(userId, addressPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{userId}/address")
    public ResponseEntity<AddressPostDto> updateUserAddress(@PathVariable Long userId,
        @RequestBody @Valid AddressPostDto addressPostDto) {
        return ResponseEntity.ok(addressService.updateUserAddress(userId, addressPostDto));
    }

    @DeleteMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        addressService.deleteUserAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }


}

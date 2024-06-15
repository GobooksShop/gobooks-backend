package org.team.bookshop.domain.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.user.dto.AddressPostDto;
import org.team.bookshop.domain.user.entity.Address;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.AddressRepository;
import org.team.bookshop.domain.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


    public List<AddressPostDto> getUserAddress(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return AddressPostDto.toDtoList(addresses);
    }

    public void saveUserAddress(Long userId, AddressPostDto addressPostDto) {
        Address address = addressPostDto.toEntity();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        address.setUser(user);
        addressRepository.save(address);
    }

    public AddressPostDto updateUserAddress(Long userId, AddressPostDto addressPostDto) {
        Address address = addressPostDto.toEntity();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        address.setUser(user);
        Address updatedAddress = addressRepository.save(address);
        return AddressPostDto.toDto(updatedAddress);
    }

    public void deleteUserAddress(Long userId) {
        addressRepository.deleteByUserId(userId);
    }

}

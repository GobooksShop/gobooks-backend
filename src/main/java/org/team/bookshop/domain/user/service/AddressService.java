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
import org.team.bookshop.global.error.exception.EntityNotFoundException;

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

    public AddressPostDto getUserAddress(Long userId, Long addressId) {
        Address address = addressRepository.findAddressByUserId(userId, addressId);
        return AddressPostDto.toDto(address);
    }

    public AddressPostDto saveUserAddress(Long userId, AddressPostDto addressPostDto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
        Address address = addressPostDto.toEntity(user);
        address = addressRepository.save(address);
        return AddressPostDto.toDto(address);
    }

    public AddressPostDto updateUserAddress(Long userId, AddressPostDto addressPostDto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
        Address address = addressPostDto.toEntity(user);
        return AddressPostDto.toDto(addressRepository.save(address));
    }

    public void deleteUserAddress(Long userId, Long addressId) {
        addressRepository.deleteById(addressId);
    }

}

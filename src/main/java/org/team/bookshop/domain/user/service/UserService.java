package org.team.bookshop.domain.user.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.order.repository.DeliveryRepository;
import org.team.bookshop.domain.order.repository.OrderRepository;
import org.team.bookshop.domain.user.dto.UserJoinDto;
import org.team.bookshop.domain.user.dto.UserPostDto;
import org.team.bookshop.domain.user.entity.Address;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.AddressRepository;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;
import org.team.bookshop.global.error.exception.EntityNotFoundException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = false)
    public void saveUser(UserJoinDto userJoinDto) throws ApiException {
        if (userRepository.findByEmail(userJoinDto.getEmail()).isPresent()) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_REGISTERED);
        }

        User user = userJoinDto.toEntity();
        user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));

        userRepository.save(user);
    }


    public UserPostDto getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        UserPostDto userPostDto = new UserPostDto();
        return userPostDto.toDto(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found with email " + email));
    }


    public List<UserPostDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserPostDto> userPostDtos = new ArrayList<>();
        for (User user : users) {
            UserPostDto userPostDto = new UserPostDto();
            userPostDto.toDto(user);
            userPostDtos.add(userPostDto);
        }
        return userPostDtos;

    }


    @Transactional(readOnly = false)
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        List<Address> addresses = addressRepository.findByUserId(id);

        for (Address address : addresses) {
            Delivery delivery = deliveryRepository.findByAddressId(address.getId());

            orderRepository.deleteByDeliveryId(delivery.getId());
            deliveryRepository.deleteByAddressId(address.getId());
        }

        addressRepository.deleteByUserId(id);
        userRepository.delete(user);
    }


    @Transactional(readOnly = false)
    public UserPostDto updateUser(Long id, UserPostDto UserPostDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        user.setEmail(UserPostDto.getEmail());
        user.setPassword(passwordEncoder.encode(UserPostDto.getPassword()));
        user.setNickname(UserPostDto.getNickname());
        user.setName(UserPostDto.getName());
        user.setPhone(UserPostDto.getPhone());
        user.setMarketingAgreed(UserPostDto.getMarketingAgreed());
        user.setEmailVerified(UserPostDto.getEmailVerified());

        userRepository.save(user);
        return UserPostDto.toDto(user);
    }
}

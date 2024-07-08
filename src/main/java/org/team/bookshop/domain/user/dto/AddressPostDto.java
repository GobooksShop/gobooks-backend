package org.team.bookshop.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.user.entity.Address;
import org.team.bookshop.domain.user.entity.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressPostDto {

    private Long id;
    @NotBlank(message = "주소별칭은 빈 값일 수 없습니다.")
    private String label;
    private Boolean isPrimary;
    @NotBlank(message = "우편번호는 빈 값일 수 없습니다.")
    private String zipcode;
    @NotBlank(message = "주소1는 빈 값일 수 없습니다.")
    private String address1;
    @NotBlank(message = "주소2는 빈 값일 수 없습니다.")
    private String address2;
    @NotBlank(message = "받는사람 이름은 빈 값일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,}$", message = "받는사람 이름은 2자리 이상 한글 또는 영문만 입력 가능합니다.")
    private String recipientName;
    @NotBlank(message = "받는사람 연락처는 빈 값일 수 없습니다.")
    @Pattern(regexp = "^\\d{10,11}$", message = "받는사람 연락처는 10자리 또는 11자리 숫자여야 합니다.")
    private String recipientPhone;

    public Address toEntity(User user) {
        return Address.builder()
            .id(id)
            .label(label)
            .user(user)
            .isPrimary(isPrimary)
            .zipcode(zipcode)
            .address1(address1)
            .address2(address2)
            .recipientName(recipientName)
            .recipientPhone(recipientPhone)
            .build();
    }

    public static AddressPostDto toDto(Address address) {
        return AddressPostDto.builder()
            .id(address.getId())
            .label(address.getLabel())
            .isPrimary(address.getIsPrimary())
            .zipcode(address.getZipcode())
            .address1(address.getAddress1())
            .address2(address.getAddress2())
            .recipientName(address.getRecipientName())
            .recipientPhone(address.getRecipientPhone())
            .build();
    }

    public static List<AddressPostDto> toDtoList(List<Address> addresses) {
        return addresses.stream()
            .map(AddressPostDto::toDto)
            .collect(Collectors.toList());
    }
}

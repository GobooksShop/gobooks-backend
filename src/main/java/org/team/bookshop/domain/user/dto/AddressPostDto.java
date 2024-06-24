package org.team.bookshop.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.domain.user.entity.Address;

@Getter
@Setter
public class AddressPostDto {

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

    public Address toEntity() {
        Address address = new Address();
        address.setLabel(label);
        address.setIsPrimary(isPrimary);
        address.setZipcode(zipcode);
        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setRecipientName(recipientName);
        address.setRecipientPhone(recipientPhone);
        return address;
    }

    public static AddressPostDto toDto(Address address) {
        AddressPostDto dto = new AddressPostDto();
        dto.setLabel(address.getLabel());
        dto.setIsPrimary(address.getIsPrimary());
        dto.setZipcode(address.getZipcode());
        dto.setAddress1(address.getAddress1());
        dto.setAddress2(address.getAddress2());
        dto.setRecipientName(address.getRecipientName());
        dto.setRecipientPhone(address.getRecipientPhone());
        return dto;
    }

    public static List<AddressPostDto> toDtoList(List<Address> addresses) {
        return addresses.stream()
            .map(AddressPostDto::toDto)
            .collect(Collectors.toList());
    }
}

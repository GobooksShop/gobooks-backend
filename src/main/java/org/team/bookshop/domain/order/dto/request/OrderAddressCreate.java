package org.team.bookshop.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.team.bookshop.domain.user.entity.Address;

@Data
public class OrderAddressCreate {

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

    public OrderAddressCreate(String zipcode, String address1, String address2, String recipientName, String recipientPhone) {
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
    }

    public Address toEntity(){
        return Address.builder()
            .zipcode(this.zipcode)
            .address1(this.address1)
            .address2(this.address2)
            .recipientName(this.recipientName)
            .recipientPhone(this.recipientPhone)
            .build();
    }
}

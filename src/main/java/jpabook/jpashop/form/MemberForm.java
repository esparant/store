package jpabook.jpashop.form;

import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.common.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원명은 필수입니다.")
    private String name;

    private Address address;
//    private String city;
//    private String street;
//    private String zipcode;
}

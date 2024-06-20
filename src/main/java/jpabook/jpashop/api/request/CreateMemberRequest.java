package jpabook.jpashop.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMemberRequest {
    @NotBlank
    @NotNull
    @NotEmpty
    private String name;
}

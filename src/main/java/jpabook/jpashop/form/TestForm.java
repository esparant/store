package jpabook.jpashop.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class TestForm {
    private Long memberId;
    private Long itemId;
    private int quantity;
}

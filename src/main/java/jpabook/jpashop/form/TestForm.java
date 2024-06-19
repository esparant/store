package jpabook.jpashop.form;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TestForm {
    private Long memberId;
    private Long itemId;
    private int quantity;
}

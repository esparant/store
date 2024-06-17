package jpabook.jpashop.ex;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MemberEx {

    @Id @GeneratedValue
    private Long id;
    private String username;

    public MemberEx(String username) {
        this.username = username;
    }
}

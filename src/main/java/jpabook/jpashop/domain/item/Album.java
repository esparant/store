package jpabook.jpashop.domain.item;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Album extends Item {

    private String artist;
    private String etc;

    public Album(String name, int price, int stock, String artist, String etc) {
        super(name, price, stock);
        this.artist = artist;
        this.etc = etc;
    }
}

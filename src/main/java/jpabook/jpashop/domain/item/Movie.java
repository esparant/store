package jpabook.jpashop.domain.item;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Movie extends Item{

    private String director;
    private String actor;


    public Movie(String name, int price, int stock, String director, String actor) {
        super(name, price, stock);
        this.director = director;
        this.actor = actor;
    }
}

package jpabook.jpashop.domain.item;

import jakarta.persistence.Entity;

@Entity
public class Album extends Item {

    private String artist;
    private String etc;
}

package jpabook.jpashop.domain.item;

import jakarta.persistence.Entity;
import jpabook.jpashop.form.BookForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbn;

    public Book(String name, int price, int stock, String author, String isbn) {
        super(name, price, stock);
        this.author = author;
        this.isbn = isbn;
    }

    public void update(BookForm form) {
        super.setName(form.getName());
        super.setPrice(form.getPrice());
        super.setStock(form.getStock());
        author = form.getAuthor();
        isbn = form.getIsbn();
    }
}

package ir.asta.warehouseone.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WH_BOOK")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public BookEntity(String isbn10, String isbn13, String title, BigDecimal price) {
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.title = title;
        this.price = price;
    }

    private String isbn10;
    private String isbn13;
    private String title;
    private BigDecimal price;

}
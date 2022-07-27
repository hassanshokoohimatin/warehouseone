package ir.asta.warehouseone.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WH_CATEGORY")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public CategoryEntity(String subject, String code) {
        this.subject = subject;
        this.code = code;
    }

    private String subject;
    private String code;
}

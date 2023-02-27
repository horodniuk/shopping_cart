package cart;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(of = {"product_id"})
@Setter(AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "store")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
    @Column(name = "product_name")
    private String name;
    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}

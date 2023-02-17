package cart;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = {"product_id"})
@Setter(AccessLevel.NONE)
public class Product {
    private int product_id;
    private String name;
    private BigDecimal price;
}

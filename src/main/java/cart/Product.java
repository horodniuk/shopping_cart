package cart;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode(of = {"product_id"})
public class Product {
    private int product_id;
    private String name;
    private BigDecimal price;
}

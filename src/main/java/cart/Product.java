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
    @Setter
    private String name;
    @Setter
    private BigDecimal price;
}

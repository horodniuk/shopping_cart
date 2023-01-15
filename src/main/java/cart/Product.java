package cart;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private String name;
    private BigDecimal price;
    private int quantity;
}

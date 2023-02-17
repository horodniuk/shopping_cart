package cart;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Getter
    private int product_id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return product_id == product.product_id;
    }

    @Override
    public int hashCode() {
        return product_id;
    }
}

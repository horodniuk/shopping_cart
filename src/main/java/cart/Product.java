package cart;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = {"product_id"})
@Setter(AccessLevel.NONE)
public class Product {
    private final int product_id;
    private final String name;
    private final BigDecimal price;
}

package cart;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor

public class Product {
    private int product_id;
    private String name;
    private BigDecimal price;

    public int getProduct_id() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

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

    @Override
    public String toString() {
        return "Product{" +
               "product_id=" + product_id +
               ", name='" + name + '\'' +
               ", price=" + price +
               '}';
    }
}

package cart;

import java.math.BigDecimal;

public class Product {
    private String name;
    private BigDecimal price;
    private int quantiti;

    public Product(String name, BigDecimal price, int quantiti) {
        this.name = name;
        this.price = price;
        this.quantiti = quantiti;
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

    public int getQuantiti() {
        return quantiti;
    }

    public void setQuantiti(int quantiti) {
        this.quantiti = quantiti;
    }

    @Override
    public String toString() {
        return "Product{" +
               "name='" + name + '\'' +
               ", price=" + price +
               ", quantiti=" + quantiti +
               '}';
    }
}

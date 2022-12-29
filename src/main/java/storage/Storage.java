package storage;

import cart.Product;

import java.util.Map;

public interface Storage {
   Map<String, Product> load(String file);
   void write (Product product);
   Map<String, Product> getStorage();
}

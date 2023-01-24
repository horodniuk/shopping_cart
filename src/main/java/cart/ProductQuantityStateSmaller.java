package cart;

public class ProductQuantityStateSmaller extends ProductQuantityState {
    @Override
    public boolean checkProductQuantity(int quantityInCart, int neededQuantity) {
        return quantityInCart < neededQuantity;
    }

    @Override
    public void invokeAction(String productName, int quantityInCart, int neededQuantity, Cart cart) {
        System.out.printf("Cart doesn't contain %s in quantity %d right now there is only next quantity: %d%n",
                productName, neededQuantity, quantityInCart);
    }
}

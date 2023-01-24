package cart;


public class ProductQuantityStateEquals extends ProductQuantityState {
    @Override
    public boolean checkProductQuantity(int quantityInCart, int neededQuantity) {
        return quantityInCart == neededQuantity;
    }

    @Override
    public void invokeAction(String productName, int quantityInCart, int neededQuantity, Cart cart) {
        cart.deleteProductAndDiscount(productName, neededQuantity);
    }
}

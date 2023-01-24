package cart;

public abstract class ProductQuantityState {

    public abstract boolean checkProductQuantity(int quantityInCart, int neededQuantity);

    public abstract void invokeAction(String productName, int quantityInCart, int neededQuantity, Cart cart);
}

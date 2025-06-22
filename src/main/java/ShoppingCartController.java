import java.math.BigDecimal;
import java.util.Collection;


public class ShoppingCartController {

    private ShoppingCart cart;

    public ShoppingCartController() {
        this.cart = new ShoppingCart();
    }

    // 取得購物車內容
    public Collection<ShoppingCartItem> getCartContents() {
        return this.cart.getItems();
    }

    // 將商品加入購物車
    public void order(Product product, int quantity) {
        this.cart.addItem(product, quantity);
    }

    // 修改某個商品訂單的數量
    public boolean updateQuantity(String productId, int newQuantity) {
        return this.cart.updateQuantity(productId, newQuantity);
    }

    // 刪除某個商品的訂單
    public boolean removeItem(String productId) {
        return this.cart.removeItem(productId);
    }

    // 計算所有訂單金和總合
    public BigDecimal calculateTotal() {
        return this.cart.getTotal();
    }

    // 判斷購物車是否為空
    public boolean isCartEmpty() {
        return this.cart.isEmpty();
    }
}

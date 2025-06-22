import java.math.BigDecimal;

public class ShoppingCartItem {

    private final Product product;
    private int quantity;

    public ShoppingCartItem(Product product, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity>0");
        this.product = product;
        this.quantity = quantity;
    }

    //累加數量，方便 ShoppingCart 的 addItem 方法進行 merge
    public void addQuantity(int delta) {
        if (delta <= 0) return;
        this.quantity += delta;
    }

    // 取得訂單小計
    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    // getters and setter
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        // 參數校驗
        if (quantity <= 0) throw new IllegalArgumentException("quantity>0");
        this.quantity = quantity;
    }
}

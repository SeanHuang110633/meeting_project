import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    /**
     * key : productId
     * value : CartItem
     * 用 LinkedHashMap 為持加入時的順序
     */
    private final Map<String, ShoppingCartItem> items = new LinkedHashMap<>();

    // 新增訂單(相同商品的訂單已經存在的話項就修改數量)
    public void addItem(Product p, int qty) {
        if (qty <= 0) return;

        // 如果 items 中已經有這個商品，就增加數量；沒有的話就新增一個ShoppingCartItem
        items.merge(p.getProductId(),
                new ShoppingCartItem(p, qty),
                (oldItem, newItem) -> {   // 這邊沒有用到 newItem 不用理他
                    oldItem.addQuantity(qty);
                    return oldItem;
                });
    }

    // 修改某個商品訂單的數量
    public boolean updateQuantity(String id, int qty) {
        if (qty <= 0) return false;
        ShoppingCartItem item = items.get(id);
        if (item == null) return false;
        item.setQuantity(qty);
        return true;
    }

    // 刪除訂單
    public boolean removeItem(String id) {
        return items.remove(id) != null;
    }

    // 取得所有訂單(提供的是不可修改的副本)
    public Collection<ShoppingCartItem> getItems() {
        return List.copyOf(items.values());
    }

    // 取得所有訂單的金額總合
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ShoppingCartItem item : items.values()) {
            total = total.add(item.getSubtotal());
        }

        return total;
    }

    // 判空
    public boolean isEmpty() {
        return items.isEmpty();
    }
}

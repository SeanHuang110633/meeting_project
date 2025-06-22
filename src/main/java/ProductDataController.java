import java.util.*;

/**
 * 產品資料存取／查詢控制器（ArrayList + Map 版本）
 */
public class ProductDataController {

    /**
     * key : catalogId
     * value : 該目錄下所有商品
     **/
    private final Map<String, List<Product>> catalogMap = new HashMap<>();

    /**
     * key : productId
     * value : Product 實例
     **/
    private final Map<String, Product> productMap  = new HashMap<>();


    public ProductDataController(List<Product> products) {
        for (Product p : products) {
            // 建立 catalog
            catalogMap.computeIfAbsent(p.getCatalogId(), id -> new ArrayList<>()).add(p);

            // 建立 productId 索引
            productMap.put(p.getProductId(), p);
        }
    }

    // 取得所有目錄名稱（以 List 回傳避免接收者修改）
    public List<String> getCatalogContents() {
        return List.copyOf(catalogMap.keySet());
    }

    // 取得某目錄下的所有商品；若無該目錄回傳空 List
    public List<Product> getProducts(String catalogId) {
        return catalogMap.getOrDefault(catalogId, List.of());
    }

    // 依商品編號取得商品；找不到會回傳 null
    public Product getProduct(String productId) {
        return productMap.get(productId);
    }
}

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

@SuppressWarnings("all")
public class OnlineShoppingApp {

    private final ProductDataController pdc;
    private final ShoppingCartController scc;

    public OnlineShoppingApp() {
        this.pdc = initProducts();
        this.scc = new ShoppingCartController();
    }


    public static void main(String[] args) {
        OnlineShoppingApp onlineShoppingApp = new OnlineShoppingApp();
        onlineShoppingApp.start();
    }

    /**流程指令枚舉 : 顯示購物車資訊後，下一步的指令選項
     * CONTINUE_SAME_CATALOG : 在同個商品目錄下繼續選購
     * BACK_TO_CATALOG : 回到目錄選其他類的商品
     * EXIT : 直接退出
     **/
    private enum Action { CONTINUE_SAME_CATALOG, BACK_TO_CATALOG, EXIT }

    // 主流程
    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {                                 // 目錄層
                String catalog = askCatalog(scanner);      // ← 顯示目錄清單，如果返回null就EXIT
                if (catalog == null) break;

                while (true) {                             // 商品層
                    Product p = askProduct(scanner, catalog); // 顯示商品清單並返回所選商品，如果返回 null 就 BACK 到上一層
                    if (p == null) break;

                    int qty = askQuantity(scanner);        // 輸入商品數量
                    if (qty == 0) continue;                // 如果返回 0 就返回上一步重選商品

                    scc.order(p, qty);                     // 將商品加入購物車
                    showCart();                            // 顯示購物車內容

                    // 決定下一步要繼續選購(回到目錄清單或商品清單)或退出系統
                    Action act = askNextAction(scanner);
                    if (act == Action.EXIT)  return;       // 退出系統
                    if (act == Action.BACK_TO_CATALOG) break; // 回到目錄層
                    /* CONTINUE_SAME_CATALOG -> 什麼都不做，留在同個商品層 */
                }
            }
        }
        System.out.println("================== 退出系統 Bye~ =====================");
    }


    // 取得商品目錄，輸入 exit 則回傳 null，主流程會判定退出系統
    private String askCatalog(Scanner sc) {
        while (true) {
            System.out.println("================== 開始選購 =====================");
            System.out.println("商品目錄清單 : " + pdc.getCatalogContents());
            System.out.print("請選擇目錄 (輸入 exit 離開) : ");
            String in = sc.nextLine().trim();
            if ("exit".equalsIgnoreCase(in)) return null;
            if (pdc.getCatalogContents().contains(in)) return in;
            System.out.println("❌ 目錄不存在，請重新輸入！");
        }
    }

    // 顯示目錄下的商品清單並於用戶選擇商品後顯示商品詳情
    private Product askProduct(Scanner sc, String catalog) {
        while (true) {
            System.out.println("------------ 目錄<<" + catalog + ">>商品 ------------");
            for (Product p : pdc.getProducts(catalog)) {
                System.out.printf("商品編號[%s] %s  $%.0f%n", p.getProductId(), p.getName(), p.getPrice());
            }
            System.out.print("請輸入商品編號 (輸入 back 回上層) : ");
            String id = sc.nextLine().trim();
            if ("back".equalsIgnoreCase(id)) return null;
            Product p = pdc.getProduct(id);
            if (p != null) {
                System.out.println("++++++++++++++++++ 商品詳情 +++++++++++++++++++");
                System.out.println("商品名稱 : " + p.getName());
                System.out.println("商品描述 : " + p.getDescription());
                System.out.println("單價 : " + p.getPrice());
                return p;
            }
            System.out.println("❌ 商品編號錯誤，請重新輸入！");
        }
    }

    // 輸入商品購買數量，數量必須是大於 0 的整數
    private int askQuantity(Scanner sc) {
        while (true) {
            System.out.print("請輸入購買數量 (輸入 back 回上層) : ");
            String q = sc.nextLine().trim();
            if ("back".equalsIgnoreCase(q)) return 0;
            try {
                int qty = Integer.parseInt(q);
                if (qty > 0) return qty;
            } catch (NumberFormatException ignored) { }
            System.out.println("❌ 請輸入大於 0 的整數！");
        }
    }

    // 顯示購物車內容
    private void showCart() {
        System.out.println("++++++++++++++++++ 購物車資訊 +++++++++++++++++++");
        int no = 1;
        for (ShoppingCartItem i : scc.getCartContents()) {
            System.out.printf("[%d] 商品名稱: %s  數量: %d  小計: $%.0f%n",
                    no, i.getProduct().getName(), i.getQuantity(), i.getSubtotal());
            no++;
        }
        System.out.println("所有訂單合計 : $" + scc.calculateTotal());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    /** 詢問下一步動作，回傳 enum Action
     * CONTINUE_SAME_CATALOG : 在同個商品目錄下繼續選購
     * BACK_TO_CATALOG : 回到目錄選其他類的商品
     * EXIT : 直接退出
     */
    private Action askNextAction(Scanner sc) {
        while (true) {
            System.out.print("Y=回商品清單、YY=回目錄清單、ENTER=結束 : ");
            String in = sc.nextLine().trim();
            if (in.isEmpty())  return Action.EXIT;
            if ("Y".equalsIgnoreCase(in))  return Action.CONTINUE_SAME_CATALOG;
            if ("YY".equalsIgnoreCase(in)) return Action.BACK_TO_CATALOG;
            System.out.println("❌ 指令錯誤，請重新輸入！");
        }
    }


    // 初始化方法
    private ProductDataController initProducts() {
        // 初始化商品
        List<Product> products = List.of(
                new Product("S1", "shoesA", "for running", BigDecimal.valueOf(1000), "shoes"),
                new Product("S2", "shoesB", "for training", BigDecimal.valueOf(1500), "shoes"),
                new Product("S3", "shoesC", "for soccer",   BigDecimal.valueOf(2000), "shoes"),
                new Product("C1", "clothA", "T-shirt",      BigDecimal.valueOf(700),  "clothes"),
                new Product("C2", "clothB", "jacket",       BigDecimal.valueOf(1000), "clothes"),
                new Product("C3", "clothC", "shirt",        BigDecimal.valueOf(1500), "clothes")
        );

        // 提供給 constructor 初始化 ProductDataController
        return new ProductDataController(products);
    }
}




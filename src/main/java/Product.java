import java.math.BigDecimal;

public class Product {

    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String catalogId;

    public Product(String productId, String name, String description, BigDecimal price, String catalogId) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.catalogId = catalogId;
    }


    // getter
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCatalogId() {
        return catalogId;
    }
}

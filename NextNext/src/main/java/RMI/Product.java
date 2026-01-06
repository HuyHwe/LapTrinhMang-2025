package RMI;

import java.io.Serializable;

public class Product implements Serializable {
    public String id;
    public String code;
    public double importPrice;
    public double exportPrice;
    public static final long serialVersionUID = 20151107;

    public Product(String id, String code, double importPrice, double exportPrice) {
        this.id = id;
        this.code = code;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", importPrice=" + importPrice +
                ", exportPrice=" + exportPrice +
                '}';
    }
}

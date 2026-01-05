package TCP;

import java.io.Serializable;

public class Laptop implements Serializable {
    public int id;
    public String name;
    public String code;
    public int quantity;
    private static final long serialVersionUID = 20150711L;

    public Laptop(int id, String name, String code, int quantity) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.quantity = quantity;
    }

    public String toString() {
        return "ID: " + Integer.toString(id) + " name: " + name + " code: " + code + " quant: " + Integer.toString(quantity);
    }
}

package TCP;

import java.io.Serializable;

public class Customer implements Serializable {
    public int id;
    public String code;
    public String name;
    public String dayOfBirth;
    public String userName;
    private static final long serialVersionUID = 20170711L;
    public Customer(int id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }
}

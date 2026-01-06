package UDP;

import java.io.Serializable;

public class Student implements Serializable {
    public String id;
    public String code;
    public String name;
    public String email;
    private static final long serialVersionUID = 20171107;

    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }

    public Student(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Student - " + "id: " + id + " code: " + code + " name: " + name + " code: " + code + " email: " + email;

    }
}

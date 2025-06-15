import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String name;
    String description;
    byte perm;
    
    public Role(String name, String description, byte perm) {
        this.name = name;
        this.description = description;
        this.perm = perm;
    }
}
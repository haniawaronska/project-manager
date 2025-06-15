import java.io.Serializable;
import java.util.*;

public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String name;
    String desc;
    Map<User, Role> members;
    
    public Team(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.members = new HashMap<>();
    }
    
    public void addMember(User user, Role role) {
        members.put(user, role);
    }
    
    public void removeMember(User user) {
        members.remove(user);
    }
    
    public Role getUserRole(User user) {
        return members.get(user);
    }
    
    public boolean hasMember(User user) {
        return members.containsKey(user);
    }

    @Override
    public String toString() {
        return name;
    }
}
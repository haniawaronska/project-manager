import javax.swing.*;

public class AuthManager {
    private static User activeUser;
    
    public static User getActiveUser() {
        return activeUser;
    }
    
    public static void setActiveUser(User user) {
        activeUser = user;
    }
    
    public static boolean isLoggedIn() {
        return activeUser != null;
    }
    
    public static void showLoginDialog(JFrame parent, Runnable onSuccess) {
        // Tymczasowa implementacja - tworzy testowego użytkownika
        activeUser = new User("Test User", "test@example.com", "Password123!", false);
        if (onSuccess != null) {
            onSuccess.run();
        }
    }
    
    public static void logout() {
        activeUser = null;
    }
}
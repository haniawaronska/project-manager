import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;


class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Utworzenie kontenera i przykładowych projektów
            Container container = new Container();
            int startDay = (int) (System.currentTimeMillis() / (1000L * 60 * 60 * 24));
            for (int i = 1; i <= 5; i++) {
                int deadlineDay = startDay + 7;
                Project p = new Project(
                        "Projekt " + i,
                        "Opis projektu nr " + i,
                        startDay,
                        deadlineDay
                );
                container.addProject(p);
            }

            // Utworzenie okna i dodanie DashboardView
            JFrame frame = new JFrame("Project Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

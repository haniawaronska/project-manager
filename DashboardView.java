import javax.swing.*;
import java.awt.*;

public class DashboardView extends JPanel {
    public DashboardView() {
        this.setLayout(new BorderLayout());
        this.add(new JLabel("Dashboard View: Here will be your projects."), BorderLayout.CENTER);
    }
}

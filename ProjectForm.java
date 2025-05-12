import javax.swing.*;
import java.awt.*;

public class ProjectForm extends JPanel {
    public ProjectForm() {
        this.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Project Name:");
        JTextField nameField = new JTextField();
        this.add(nameLabel, BorderLayout.NORTH);
        this.add(nameField, BorderLayout.CENTER);
    }
}

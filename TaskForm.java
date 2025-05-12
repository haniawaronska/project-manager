import javax.swing.*;
import java.awt.*;

public class TaskForm extends JPanel {
    public TaskForm() {
        this.setLayout(new BorderLayout());

        JLabel taskLabel = new JLabel("Task Name:");
        JTextField taskField = new JTextField();
        this.add(taskLabel, BorderLayout.NORTH);
        this.add(taskField, BorderLayout.CENTER);
    }
}

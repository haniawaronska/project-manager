import javax.swing.*;
import java.awt.*;

public class ProjectDetailView extends JPanel {
    private Project project;

    public ProjectDetailView(Project project) {
        this.project = project;
        this.setLayout(new BorderLayout());
        this.add(new JLabel("Project Details for: " + project.name), BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea(project.descript);
        descriptionArea.setEditable(false);
        this.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
    }
}

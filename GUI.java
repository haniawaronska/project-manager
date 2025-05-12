import javax.swing.*;
import java.awt.*;

public class GUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel contentPanel; // Panel z CardLayout
    private CardLayout cardLayout;
    private Container container;

    public GUI(Container container) {
        this.container = container;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Project Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        mainPanel = new JPanel(new BorderLayout());

        // Lewy panel z przyciskami do zmiany widoku
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(200, 0));

        JButton dashboardBtn = new JButton("Dashboard");
        dashboardBtn.addActionListener(e -> cardLayout.show(contentPanel, "dashboard"));
        leftPanel.add(dashboardBtn);

        JButton projectFormBtn = new JButton("Dodaj Projekt");
        projectFormBtn.addActionListener(e -> cardLayout.show(contentPanel, "projectForm"));
        leftPanel.add(projectFormBtn);

        JButton taskFormBtn = new JButton("Dodaj Zadanie");
        taskFormBtn.addActionListener(e -> cardLayout.show(contentPanel, "taskForm"));
        leftPanel.add(taskFormBtn);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Panel centralny - dynamiczne widoki
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new DashboardView(), "dashboard");
        contentPanel.add(new ProjectForm(), "projectForm");
        contentPanel.add(new TaskForm(), "taskForm");

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
        mainPanel.setBackground(new Color(240, 240, 240)); // jasnoszary

    }
}

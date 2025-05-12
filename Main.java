import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Container container = new Container();

        Project p = new Project("Nowy projekt", "Opis", 19500, 19530);
        container.addProject(p);

        // uruchamiamy GUI
        SwingUtilities.invokeLater(() -> new GUI(container));
    }
}

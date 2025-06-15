import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class SettingsWindow extends JDialog {
    private final Container container;
    private final User currentUser;
    private JList<Team> teamList;
    private DefaultListModel<Team> teamListModel;
    
    public SettingsWindow(JFrame parent, Container container) {
        super(parent, "Ustawienia", true);
        this.container = container;
        this.currentUser = AuthManager.getActiveUser();
        
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Zakładka Zespoły
        JPanel teamPanel = createTeamPanel();
        tabbedPane.addTab("Zespoły", teamPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Przyciski
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Zamknij");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTeamPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Lista zespołów
        teamListModel = new DefaultListModel<>();
        refreshTeamList();
        teamList = new JList<>(teamListModel);
        teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(teamList);
        scrollPane.setPreferredSize(new Dimension(200, 0));
        
        // Panel przycisków dla zespołów
        JPanel teamButtonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        JButton createTeamButton = new JButton("Utwórz zespół");
        JButton editTeamButton = new JButton("Edytuj zespół");
        JButton deleteTeamButton = new JButton("Usuń zespół");
        
        createTeamButton.addActionListener(this::createTeam);
        editTeamButton.addActionListener(this::editTeam);
        deleteTeamButton.addActionListener(this::deleteTeam);
        
        teamButtonPanel.add(createTeamButton);
        teamButtonPanel.add(editTeamButton);
        teamButtonPanel.add(deleteTeamButton);
        
        // Panel członków zespołu
        JPanel membersPanel = createMembersPanel();
        
        // Layout
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("Zespoły:"), BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        leftPanel.add(teamButtonPanel, BorderLayout.SOUTH);
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(membersPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createMembersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Członkowie zespołu"));
        
        // Lista członków
        JList<String> membersList = new JList<>();
        JScrollPane membersScroll = new JScrollPane(membersList);
        
        // Przyciski członków
        JPanel memberButtonPanel = new JPanel(new FlowLayout());
        JButton addMemberButton = new JButton("Dodaj członka");
        JButton removeMemberButton = new JButton("Usuń członka");
        JButton changeRoleButton = new JButton("Zmień rolę");
        
        addMemberButton.addActionListener(this::addMember);
        removeMemberButton.addActionListener(this::removeMember);
        changeRoleButton.addActionListener(this::changeRole);
        
        memberButtonPanel.add(addMemberButton);
        memberButtonPanel.add(removeMemberButton);
        memberButtonPanel.add(changeRoleButton);
        
        panel.add(membersScroll, BorderLayout.CENTER);
        panel.add(memberButtonPanel, BorderLayout.SOUTH);
        
        // Aktualizacja listy członków przy zmianie zespołu
        teamList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateMembersList(membersList);
            }
        });
        
        return panel;
    }
    
    private void refreshTeamList() {
        teamListModel.clear();
        if (currentUser != null && currentUser.teams != null) {
            for (Team team : currentUser.teams.keySet()) {
                teamListModel.addElement(team);
            }
        }
    }
    
    private void updateMembersList(JList<String> membersList) {
        Team selectedTeam = teamList.getSelectedValue();
        if (selectedTeam != null) {
            DefaultListModel<String> model = new DefaultListModel<>();
            for (Map.Entry<User, Role> entry : selectedTeam.members.entrySet()) {
                model.addElement(entry.getKey().name + " (" + entry.getValue().name + ")");
            }
            membersList.setModel(model);
        }
    }
    
    private void createTeam(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, "Nazwa zespołu:");
        if (name != null && !name.trim().isEmpty()) {
            String desc = JOptionPane.showInputDialog(this, "Opis zespołu:");
            if (desc == null) desc = "";
            
            Team newTeam = new Team(name.trim(), desc.trim());
            Role adminRole = new Role("Administrator", "Administrator Zespołu", (byte) 127);
            
            newTeam.addMember(currentUser, adminRole);
            
            if (currentUser.teams == null) {
                currentUser.teams = new HashMap<>();
            }
            currentUser.teams.put(newTeam, adminRole);
            
            refreshTeamList();
            Main.saveProjects(container);
            
            JOptionPane.showMessageDialog(this, "Zespół utworzony pomyślnie!");
        }
    }
    
    private void editTeam(ActionEvent e) {
        Team selectedTeam = teamList.getSelectedValue();
        if (selectedTeam == null) {
            JOptionPane.showMessageDialog(this, "Wybierz zespół do edycji.");
            return;
        }
        
        String newName = JOptionPane.showInputDialog(this, "Nowa nazwa zespołu:", selectedTeam.name);
        if (newName != null && !newName.trim().isEmpty()) {
            String newDesc = JOptionPane.showInputDialog(this, "Nowy opis zespołu:", selectedTeam.desc);
            if (newDesc == null) newDesc = "";
            
            selectedTeam.name = newName.trim();
            selectedTeam.desc = newDesc.trim();
            
            refreshTeamList();
            Main.saveProjects(container);
            
            JOptionPane.showMessageDialog(this, "Zespół zaktualizowany!");
        }
    }
    
    private void deleteTeam(ActionEvent e) {
        Team selectedTeam = teamList.getSelectedValue();
        if (selectedTeam == null) {
            JOptionPane.showMessageDialog(this, "Wybierz zespół do usunięcia.");
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, 
            "Czy na pewno chcesz usunąć zespół '" + selectedTeam.name + "'?", 
            "Potwierdzenie", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            currentUser.teams.remove(selectedTeam);
            refreshTeamList();
            Main.saveProjects(container);
            
            JOptionPane.showMessageDialog(this, "Zespół usunięty!");
        }
    }
    
    private void addMember(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Funkcja dodawania członków wymaga implementacji systemu użytkowników.");
    }
    
    private void removeMember(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Funkcja usuwania członków wymaga implementacji systemu użytkowników.");
    }
    
    private void changeRole(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Funkcja zmiany ról wymaga implementacji systemu użytkowników.");
    }
}
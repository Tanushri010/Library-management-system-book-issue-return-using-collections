import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class App extends JFrame {
    private static final Color PAGE_BACKGROUND = new Color(241, 245, 249);
    private static final Color HERO_BACKGROUND = new Color(15, 23, 42);
    private static final Color HERO_ACCENT = new Color(56, 189, 248);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color CARD_BORDER = new Color(203, 213, 225);
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);

    private final mediatheque M = new mediatheque();

    public App() {
        GUI();
    }

    void GUI() {
        setTitle("St.Vincent Pallotti Campus Library Management System");
        setSize(1180, 760);
        setMinimumSize(new Dimension(1000, 680));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 28));
        mainPanel.setBackground(PAGE_BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        mainPanel.add(createHeroSection(), BorderLayout.NORTH);
        mainPanel.add(createContentSection(), BorderLayout.CENTER);

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createHeroSection() {
        JPanel heroPanel = new JPanel(new BorderLayout(0, 16));
        heroPanel.setBackground(HERO_BACKGROUND);
        heroPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(30, 41, 59), 1, true),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)
        ));

        JLabel eyebrow = new JLabel("Campus Resource Console");
        eyebrow.setForeground(HERO_ACCENT);
        eyebrow.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel title = new JLabel("<html>St. Vincent Pallotti Campus<br>Library Management System</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));

        JLabel subtitle = new JLabel("<html>Manage books, members, and lending workflows from one clear dashboard.</html>");
        subtitle.setForeground(new Color(191, 219, 254));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JPanel textBlock = new JPanel();
        textBlock.setOpaque(false);
        textBlock.setLayout(new BoxLayout(textBlock, BoxLayout.Y_AXIS));
        textBlock.add(eyebrow);
        textBlock.add(Box.createRigidArea(new Dimension(0, 10)));
        textBlock.add(title);
        textBlock.add(Box.createRigidArea(new Dimension(0, 12)));
        textBlock.add(subtitle);

        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        badgePanel.setOpaque(false);

        JLabel badge = new JLabel("Creator: Tanushri Tonde");
        badge.setOpaque(true);
        badge.setBackground(new Color(30, 41, 59));
        badge.setForeground(new Color(224, 242, 254));
        badge.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        badge.setFont(new Font("Segoe UI", Font.BOLD, 13));
        badgePanel.add(badge);

        heroPanel.add(textBlock, BorderLayout.CENTER);
        heroPanel.add(badgePanel, BorderLayout.EAST);

        return heroPanel;
    }

    private JPanel createContentSection() {
        JPanel contentPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        contentPanel.setOpaque(false);

        JPanel booksPanel = createSectionCard(
                "Book Management",
                "Organize the collection, search titles, and browse categories."
        );
        addSectionButton(booksPanel, "Add or Remove a Book", "Create new entries or remove old ones from the collection.", e -> M.eventB1());
        addSectionButton(booksPanel, "Search for a Book", "Find a title quickly using ID or book name.", e -> M.eventB2());
        addSectionButton(booksPanel, "Display All Books", "Open the full catalog in a table view.", e -> M.eventB34("*"));
        addSectionButton(booksPanel, "Books by Category", "Filter the catalog by category and inspect matching titles.", e -> M.eventB34("A"));

        JPanel membersPanel = createSectionCard(
                "Members Management",
                "Handle member registration records and maintain member access."
        );
        addSectionButton(membersPanel, "Add a Member", "Register a new member with contact information.", e -> M.eventB5());
        addSectionButton(membersPanel, "Remove a Member", "Delete an existing member when no books are active.", e -> M.eventB6());
        addSectionButton(membersPanel, "Display Members", "View all registered members in a clean table.", e -> M.eventB7());

        JPanel lendingPanel = createSectionCard(
                "Lending Management",
                "Control issue and return actions and review current borrow records."
        );
        addSectionButton(lendingPanel, "Borrow or Return", "Issue a book or accept a return from the lending desk.", e -> M.eventB8());
        addSectionButton(lendingPanel, "Display Borrowed Books", "Review all currently borrowed books.", e -> M.eventB9());

        contentPanel.add(booksPanel);
        contentPanel.add(membersPanel);
        contentPanel.add(lendingPanel);

        return contentPanel;
    }

    private JPanel createSectionCard(String titleText, String subtitleText) {
        JPanel card = new JPanel(new BorderLayout(0, 20));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                BorderFactory.createEmptyBorder(22, 20, 20, 20)
        ));

        JPanel headerPanel = new JPanel(new BorderLayout(0, 8));
        headerPanel.setOpaque(false);

        JLabel title = new JLabel(titleText);
        title.setForeground(TEXT_PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel("<html><div style='width:260px;'>" + subtitleText + "</div></html>");
        subtitle.setForeground(TEXT_SECONDARY);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(226, 232, 240));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.CENTER);
        headerPanel.add(separator, BorderLayout.SOUTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(buttonsPanel, BorderLayout.CENTER);

        return card;
    }

    private void addSectionButton(JPanel card, String title, String description, java.awt.event.ActionListener listener) {
        JPanel buttonsPanel = (JPanel) card.getComponent(1);
        MultilineButton button = new MultilineButton(title, description);
        button.addActionListener(listener);
        buttonsPanel.add(button);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 12)));
    }

    public static void main(String[] args) throws Exception {
        System.out.println("St. Vincent Pallotti Campus<br>Library Management System Is Up And Running !");
        new App();
    }
}

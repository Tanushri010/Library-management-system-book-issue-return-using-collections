import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class mediatheque {
    private static final Color DIALOG_BACKGROUND = new Color(241, 245, 249);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color CARD_BORDER = new Color(203, 213, 225);
    private static final Color TITLE_COLOR = new Color(15, 23, 42);
    private static final Color SUBTITLE_COLOR = new Color(100, 116, 139);
    private static final Color FIELD_BACKGROUND = new Color(248, 250, 252);
    private static final Color ACTION_COLOR = new Color(14, 116, 144);

    private final LibraryManager manager;

    public mediatheque() {
        manager = new LibraryManager();
        configureDialogTheme();
    }

    private void configureDialogTheme() {
        UIManager.put("OptionPane.background", DIALOG_BACKGROUND);
        UIManager.put("Panel.background", DIALOG_BACKGROUND);
        UIManager.put("OptionPane.messageForeground", TITLE_COLOR);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("Button.background", CARD_BACKGROUND);
        UIManager.put("Button.foreground", TITLE_COLOR);
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        label.setForeground(TITLE_COLOR);
        return label;
    }

    private JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel("<html><div style='width:320px;'>" + text + "</div></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(SUBTITLE_COLOR);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(FIELD_BACKGROUND);
        field.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }

    private JPanel createDialogCard(String title, String subtitle) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(DIALOG_BACKGROUND);

        JPanel card = new JPanel(new BorderLayout(0, 18));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(createTitleLabel(title));
        header.add(Box.createRigidArea(new Dimension(0, 8)));
        header.add(createSubtitleLabel(subtitle));

        card.add(header, BorderLayout.NORTH);
        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createFormContent(String title, String subtitle) {
        JPanel wrapper = createDialogCard(title, subtitle);
        JPanel card = (JPanel) wrapper.getComponent(0);
        JPanel form = new JPanel(new GridLayout(0, 2, 12, 12));
        form.setOpaque(false);
        card.add(form, BorderLayout.CENTER);
        return form;
    }

    private void addField(JPanel form, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TITLE_COLOR);
        form.add(label);
        form.add(field);
    }

    private int showStyledConfirm(JPanel panel, String title) {
        return JOptionPane.showConfirmDialog(null, panel.getParent(), title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    private int showStyledChoice(String title, String subtitle, Object[] options) {
        JPanel panel = createDialogCard(title, subtitle);
        return JOptionPane.showOptionDialog(null, panel, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    private String showSingleInputDialog(String title, String subtitle, String labelText) {
        JPanel form = createFormContent(title, subtitle);
        JTextField field = createTextField();
        addField(form, labelText, field);
        int result = showStyledConfirm(form, title);
        if (result == JOptionPane.OK_OPTION) {
            return field.getText().trim();
        }
        return null;
    }

    private void showInfo(String title, String message) {
        JPanel panel = createDialogCard(title, message);
        JOptionPane.showMessageDialog(null, panel, title, JOptionPane.PLAIN_MESSAGE);
    }

    private void showError(String message) {
        JPanel panel = createDialogCard("Action could not be completed", message);
        JOptionPane.showMessageDialog(null, panel, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showStyledTable(String title, String subtitle, String[] columnNames, ArrayList<ArrayList<String>> data) {
        if (data.size() == 0) {
            showError("No matching records were found.");
            return;
        }

        Object[][] dataArray = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            ArrayList<String> row = data.get(i);
            dataArray[i] = row.toArray(new String[row.size()]);
        }

        JTable table = new JTable(dataArray, columnNames);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(226, 232, 240));
        table.setSelectionBackground(new Color(224, 242, 254));
        table.setGridColor(new Color(226, 232, 240));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(CARD_BORDER, 1, true));

        JPanel framePanel = new JPanel(new BorderLayout(0, 18));
        framePanel.setBackground(DIALOG_BACKGROUND);
        framePanel.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(createTitleLabel(title));
        header.add(Box.createRigidArea(new Dimension(0, 6)));
        header.add(createSubtitleLabel(subtitle));

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_BACKGROUND);
        tableCard.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(14, 14, 14, 14)
        ));
        tableCard.add(scrollPane, BorderLayout.CENTER);

        framePanel.add(header, BorderLayout.NORTH);
        framePanel.add(tableCard, BorderLayout.CENTER);

        JFrame frame = new JFrame(title);
        frame.setContentPane(framePanel);
        frame.setPreferredSize(new Dimension(900, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void addOeuvre(oeuvre oeuv) {
        manager.addBook(oeuv);
    }

    public boolean deleteOeuvre(oeuvre oeuv) {
        return manager.deleteBook(oeuv);
    }

    public oeuvre search(oeuvre oeuv) {
        return manager.searchBook(oeuv);
    }

    public ArrayList<ArrayList<String>> OprintAll() {
        return manager.getBooksTable("*");
    }

    public ArrayList<ArrayList<String>> OprintCat(String x) {
        return manager.getBooksTable(x);
    }

    public void addAdherent(adherent ad) {
        manager.addMember(ad);
    }

    public boolean deleteAdherent(adherent ad) {
        return manager.deleteMember(ad);
    }

    public boolean emprunter(pret pr) {
        return manager.issueBook(pr);
    }

    public boolean rendre(int bookId) {
        return manager.returnBook(bookId);
    }

    public void eventB1() {
        int choice = showStyledChoice(
                "Book Actions",
                "Choose whether you want to add a new book record or remove an existing one.",
                new Object[]{"Add a book", "Delete a book"}
        );

        if (choice == 0) {
            JPanel form = createFormContent("Add a Book", "Enter the book details to add it to the collection.");
            JTextField idField = createTextField();
            JTextField titleField = createTextField();
            JTextField categoryField = createTextField();
            JTextField authorField = createTextField();
            JTextField publisherField = createTextField();
            JTextField launchDateField = createTextField();
            JTextField statusField = createTextField();

            addField(form, "Book ID", idField);
            addField(form, "Title", titleField);
            addField(form, "Category", categoryField);
            addField(form, "Author", authorField);
            addField(form, "Publisher", publisherField);
            addField(form, "Release date", launchDateField);
            addField(form, "Status", statusField);

            int result = showStyledConfirm(form, "Add a Book");
            if (result == JOptionPane.OK_OPTION) {
                try {
                    addOeuvre(new oeuvre(
                            Integer.parseInt(idField.getText().trim()),
                            titleField.getText(),
                            categoryField.getText(),
                            authorField.getText(),
                            publisherField.getText(),
                            launchDateField.getText(),
                            statusField.getText()
                    ));
                    showInfo("Book Added", "The book has been added to the in-memory collection.");
                } catch (NumberFormatException e) {
                    showError("Book ID must be a number.");
                }
            }
        } else if (choice == 1) {
            int choice1 = showStyledChoice(
                    "Delete a Book",
                    "Choose how you want to identify the book you want to remove.",
                    new Object[]{"Selection by Title", "Selection by ID"}
            );

            if (choice1 == 0) {
                String value = showSingleInputDialog("Delete by Title", "Enter the title of the book you want to remove.", "Book title");
                if (value != null && !deleteOeuvre(new oeuvre(0, value, null, null, null, null, null))) {
                    showError("Book not found or currently borrowed.");
                }
            } else if (choice1 == 1) {
                String value = showSingleInputDialog("Delete by ID", "Enter the numeric book ID you want to remove.", "Book ID");
                if (value != null) {
                    try {
                        if (!deleteOeuvre(new oeuvre(Integer.parseInt(value), null, null, null, null, null, null))) {
                            showError("Book not found or currently borrowed.");
                        }
                    } catch (NumberFormatException e) {
                        showError("Book ID must be a number.");
                    }
                }
            }
        }
    }

    public void eventB2() {
        String value = showSingleInputDialog("Search for a Book", "Enter a book ID or title to search the collection.", "Book ID or title");
        if (value == null) {
            return;
        }

        oeuvre oeu;
        try {
            oeu = search(new oeuvre(Integer.parseInt(value), null, null, null, null, null, null));
        } catch (NumberFormatException e) {
            oeu = search(new oeuvre(0, value, null, null, null, null, null));
        }

        if (oeu != null) {
            String message = "<html><div style='width:300px;'>"
                    + "<b style='color:#0f172a;'>Book Details</b><br><br>"
                    + "<b>ID:</b> " + oeu.getId() + "<br>"
                    + "<b>Category:</b> " + oeu.getCategorie() + "<br>"
                    + "<b>Title:</b> " + oeu.getTitre() + "<br>"
                    + "<b>Publisher:</b> " + oeu.getEditeur() + "<br>"
                    + "<b>Release date:</b> " + oeu.getLaunchdate() + "<br>"
                    + "<b>Status:</b> " + oeu.getStatut()
                    + "</div></html>";
            showInfo("Search Result", message);
        } else {
            showError("No book matched the provided ID or title.");
        }
    }

    public void eventB34(String x) {
        if (!x.equals("*")) {
            String value = showSingleInputDialog("Filter by Category", "Enter the category you want to display.", "Category");
            if (value == null) {
                return;
            }
            x = value;
        }

        String[] columnNames = {"ID", "Title", "Category", "Author", "Publisher", "Release date", "Status"};
        ArrayList<ArrayList<String>> data = manager.getBooksTable(x);
        showStyledTable("Books Catalog", "Browse the book records currently available in memory.", columnNames, data);
    }

    public void eventB5() {
        JPanel form = createFormContent("Add a Member", "Create a new member record with identification and contact details.");
        JTextField numeroAd = createTextField();
        JTextField nom = createTextField();
        JTextField prenom = createTextField();
        JTextField adresse = createTextField();
        JTextField email = createTextField();

        addField(form, "Member ID", numeroAd);
        addField(form, "Last name", nom);
        addField(form, "First name", prenom);
        addField(form, "Address", adresse);
        addField(form, "E-mail", email);

        int result = showStyledConfirm(form, "Add a Member");
        if (result == JOptionPane.OK_OPTION) {
            addAdherent(new adherent(numeroAd.getText().trim(), nom.getText(), prenom.getText(), adresse.getText(), email.getText()));
            showInfo("Member Added", "The member has been added to the collection.");
        }
    }

    public void eventB6() {
        int choice = showStyledChoice(
                "Remove a Member",
                "Choose whether you want to remove a member by ID or by e-mail address.",
                new Object[]{"Selection by ID", "Selection by E-mail"}
        );

        if (choice == 0) {
            String value = showSingleInputDialog("Delete Member by ID", "Enter the member ID to remove.", "Member ID");
            if (value != null && !deleteAdherent(new adherent(value, null, null, null, null))) {
                showError("Member not found or still has borrowed books.");
            }
        } else if (choice == 1) {
            String value = showSingleInputDialog("Delete Member by E-mail", "Enter the member e-mail address to remove.", "E-mail");
            if (value != null && !deleteAdherent(new adherent(null, null, null, null, value))) {
                showError("Member not found or still has borrowed books.");
            }
        }
    }

    public void eventB7() {
        String[] columnNames = {"ID", "Last name", "First name", "Address", "E-mail"};
        ArrayList<ArrayList<String>> data = manager.getMembersTable();
        showStyledTable("Members Directory", "Review all currently registered members.", columnNames, data);
    }

    public void eventB8() {
        int choice = showStyledChoice(
                "Lending Actions",
                "Choose whether you want to issue a book to a member or return one back to the library.",
                new Object[]{"Borrow a book", "Return a book"}
        );

        if (choice == 0) {
            JPanel form = createFormContent("Borrow a Book", "Enter the lending details to issue a book.");
            JTextField pretdate = createTextField();
            JTextField idadherent = createTextField();
            JTextField idoeuvre = createTextField();

            addField(form, "Lending date", pretdate);
            addField(form, "Member ID", idadherent);
            addField(form, "Book ID", idoeuvre);

            int result = showStyledConfirm(form, "Borrow a Book");
            if (result == JOptionPane.OK_OPTION) {
                try {
                    if (!emprunter(new pret(pretdate.getText(), idadherent.getText().trim(), Integer.parseInt(idoeuvre.getText().trim())))) {
                        showError("This lending action could not be completed. Check member ID, book ID, availability, or lending limit.");
                    } else {
                        showInfo("Book Issued", "The lending record has been added successfully.");
                    }
                } catch (NumberFormatException e) {
                    showError("Book ID must be a number.");
                }
            }
        } else if (choice == 1) {
            String value = showSingleInputDialog("Return a Book", "Enter the numeric book ID you want to mark as returned.", "Book ID");
            if (value != null) {
                try {
                    if (!rendre(Integer.parseInt(value))) {
                        showError("Book is not currently borrowed.");
                    } else {
                        showInfo("Book Returned", "The book has been marked as available again.");
                    }
                } catch (NumberFormatException e) {
                    showError("Book ID must be a number.");
                }
            }
        }
    }

    public void eventB9() {
        String[] columnNames = {"Lending date", "Member ID", "Book ID"};
        ArrayList<ArrayList<String>> data = manager.getLoansTable();
        showStyledTable("Borrowed Books", "Review all active lending records.", columnNames, data);
    }
}

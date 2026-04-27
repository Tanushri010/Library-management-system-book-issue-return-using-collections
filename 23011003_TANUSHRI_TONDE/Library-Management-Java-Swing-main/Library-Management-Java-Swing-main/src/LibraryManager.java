import java.util.ArrayList;

public class LibraryManager {
    private final ArrayList<oeuvre> books;
    private final ArrayList<adherent> members;
    private final ArrayList<pret> issuedBooks;

    public LibraryManager() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        issuedBooks = new ArrayList<>();
    }

    public void addBook(oeuvre book) {
        if (book == null || findBookById(book.getId()) != null) {
            return;
        }
        books.add(book);
    }

    public boolean deleteBook(oeuvre book) {
        if (book == null) {
            return false;
        }

        oeuvre existing = null;
        if (book.getId() != 0) {
            existing = findBookById(book.getId());
        }
        if (existing == null && book.getTitre() != null) {
            existing = findBookByTitle(book.getTitre());
        }
        if (existing == null || isBookBorrowed(existing.getId())) {
            return false;
        }

        return books.remove(existing);
    }

    public oeuvre searchBook(oeuvre book) {
        if (book == null) {
            return null;
        }

        oeuvre result = null;
        if (book.getId() != 0) {
            result = findBookById(book.getId());
        }
        if (result == null && book.getTitre() != null) {
            result = findBookByTitle(book.getTitre());
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getBooksTable(String category) {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for (oeuvre book : books) {
            if ("*".equals(category) || book.getCategorie().equalsIgnoreCase(category)) {
                ArrayList<String> row = new ArrayList<>();
                row.add(String.valueOf(book.getId()));
                row.add(book.getTitre());
                row.add(book.getCategorie());
                row.add(book.getAuteur());
                row.add(book.getEditeur());
                row.add(String.valueOf(book.getLaunchdate()));
                row.add(book.getStatut());
                rows.add(row);
            }
        }
        return rows;
    }

    public void addMember(adherent member) {
        if (member == null || member.getNumero() == null || member.getNumero().trim().isEmpty() || findMemberById(member.getNumero()) != null) {
            return;
        }
        members.add(member);
    }

    public boolean deleteMember(adherent member) {
        if (member == null) {
            return false;
        }

        adherent existing = null;
        if (member.getNumero() != null && !member.getNumero().trim().isEmpty()) {
            existing = findMemberById(member.getNumero());
        }
        if (existing == null && member.getEmail() != null) {
            existing = findMemberByEmail(member.getEmail());
        }
        if (existing == null || hasBorrowedBooks(existing.getNumero())) {
            return false;
        }

        return members.remove(existing);
    }

    public ArrayList<ArrayList<String>> getMembersTable() {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for (adherent member : members) {
            ArrayList<String> row = new ArrayList<>();
            row.add(member.getNumero());
            row.add(member.getNom());
            row.add(member.getPrenom());
            row.add(member.getAdresse());
            row.add(member.getEmail());
            rows.add(row);
        }
        return rows;
    }

    public boolean issueBook(pret loan) {
        if (loan == null) {
            return false;
        }

        adherent member = findMemberById(loan.getIdadherant());
        oeuvre book = findBookById(loan.getIdoeuvre());
        if (member == null || book == null) {
            return false;
        }
        if (isMemberAtLimit(member.getNumero()) || isBookBorrowed(book.getId())) {
            return false;
        }

        issuedBooks.add(loan);
        book.setStatut("Not Available");
        return true;
    }

    public boolean returnBook(int bookId) {
        for (int i = 0; i < issuedBooks.size(); i++) {
            pret loan = issuedBooks.get(i);
            if (loan.getIdoeuvre() == bookId) {
                issuedBooks.remove(i);
                oeuvre book = findBookById(bookId);
                if (book != null) {
                    book.setStatut("Available");
                }
                return true;
            }
        }
        return false;
    }

    public ArrayList<ArrayList<String>> getLoansTable() {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for (pret loan : issuedBooks) {
            ArrayList<String> row = new ArrayList<>();
            row.add(loan.getPretdate());
            row.add(loan.getIdadherant());
            row.add(String.valueOf(loan.getIdoeuvre()));
            rows.add(row);
        }
        return rows;
    }

    public adherent findMemberById(String id) {
        for (adherent member : members) {
            if (member.getNumero() != null && member.getNumero().equalsIgnoreCase(id)) {
                return member;
            }
        }
        return null;
    }

    public oeuvre findBookById(int id) {
        for (oeuvre book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    private adherent findMemberByEmail(String email) {
        for (adherent member : members) {
            if (member.getEmail() != null && member.getEmail().equalsIgnoreCase(email)) {
                return member;
            }
        }
        return null;
    }

    private oeuvre findBookByTitle(String title) {
        for (oeuvre book : books) {
            if (book.getTitre() != null && book.getTitre().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    private boolean isMemberAtLimit(String memberId) {
        int count = 0;
        for (pret loan : issuedBooks) {
            if (loan.getIdadherant().equalsIgnoreCase(memberId)) {
                count++;
            }
        }
        return count >= 3;
    }

    private boolean hasBorrowedBooks(String memberId) {
        for (pret loan : issuedBooks) {
            if (loan.getIdadherant().equalsIgnoreCase(memberId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBookBorrowed(int bookId) {
        for (pret loan : issuedBooks) {
            if (loan.getIdoeuvre() == bookId) {
                return true;
            }
        }
        return false;
    }
}

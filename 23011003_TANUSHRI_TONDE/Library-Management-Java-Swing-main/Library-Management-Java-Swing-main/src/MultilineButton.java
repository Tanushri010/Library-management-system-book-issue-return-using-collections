import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class MultilineButton extends JButton {
    public MultilineButton(String title, String description) {
        super("<html><div style='text-align:left;'>"
                + "<div style='font-size:15px; font-weight:700; margin-bottom:6px;'>" + title + "</div>"
                + "<div style='font-size:11px; color:#475569;'>" + description + "</div>"
                + "</div></html>");

        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.LEFT);
        setPreferredSize(new Dimension(280, 84));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 84));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBackground(new Color(248, 250, 252));
        setForeground(new Color(15, 23, 42));
        setBorder(new CompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));
        setContentAreaFilled(true);
        setOpaque(true);
    }
}

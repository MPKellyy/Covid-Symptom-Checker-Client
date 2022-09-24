import javax.swing.*;
import java.awt.*;

// TODO: Insert form design here, rename MessagePanel to FormPanel

/**
 * This class encapsulates the covid symptom form into one panel
 */
public class FormPanel extends JPanel {

    public FormPanel() {
        this.setLayout(new BorderLayout());
        JTextArea messageView = new JTextArea("Insert form design here");
        messageView.setEditable(false);
        this.add(messageView, BorderLayout.NORTH);
        this.setVisible(true);
    }

}


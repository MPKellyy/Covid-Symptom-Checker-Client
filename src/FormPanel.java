import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * This class is responsible for the form's UI presentation.
 * Current consists of 9 checkboxes, text regarding covid symptoms, and a submission button.
 * Upon user submission in ClientFrame, a new FormPanel is created.
 */
public class FormPanel extends JPanel{
    JCheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9;
    FormPanel(){
        // Creating symptom questions for form
        cb1=new JCheckBox("Cough");
        cb2=new JCheckBox("Stuffed Nose");
        cb3=new JCheckBox("Itchy Throat");
        cb4=new JCheckBox("Headache");
        cb5=new JCheckBox("Fatigue");
        cb6=new JCheckBox("Fever/Chills");
        cb7=new JCheckBox("Nausea");
        cb8=new JCheckBox("Close contact with someone exposed? (Check box for yes)");
        cb9=new JCheckBox("Up to date with vaccinations? (Check box for no)");
        setLayout(new BorderLayout());
        Box box = Box.createVerticalBox();

        // Adding a header to form
        TitledBorder border = new TitledBorder("Welcome to the COVID-19 Symptom Checklist");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        setBorder(border);

        // Adding checkboxes to form as a group
        box.add(cb1);box.add(cb2);box.add(cb3);box.add(cb4);box.add(cb5);box.add(cb6);box.add(cb7);box.add(cb8);box.add(cb9);
        add(box, BorderLayout.CENTER);
        setVisible(true);
    }
}

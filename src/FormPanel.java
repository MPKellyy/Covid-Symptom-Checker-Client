import javax.swing.*;
import java.awt.event.*;

public class FormPanel extends JPanel implements ActionListener{
    JLabel welcome;
    JCheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9;
    String cb_1 = "0", cb_2 = "0", cb_3 = "0", cb_4 = "0", cb_5 = "0", cb_6 = "0", cb_7 = "0", cb_8 = "0",cb_9 = "0";
    JButton b;
    FormPanel(){
        welcome=new JLabel("Welcome to the COVID-19 symptom checklist.");
        welcome.setBounds(50,35,300,20);
        cb1=new JCheckBox("Cough");
        cb1.setBounds(50,75,150,20);
        cb2=new JCheckBox("Stuffed Nose");
        cb2.setBounds(50,100,150,20);
        cb3=new JCheckBox("Itchy Throat");
        cb3.setBounds(50,125,150,20);
        cb4=new JCheckBox("Headache");
        cb4.setBounds(50,150,150,20);
        cb5=new JCheckBox("Fatigue");
        cb5.setBounds(50,175,150,20);
        cb6=new JCheckBox("Fever/Chills");
        cb6.setBounds(50,200,150,20);
        cb7=new JCheckBox("Nausea");
        cb7.setBounds(50,225,150,20);
        cb8=new JCheckBox("Close contact with someone exposed? (Check box for yes)");
        cb8.setBounds(50,250,500,20);
        cb9=new JCheckBox("Up to date with vaccinations? (Check box for yes)");
        cb9.setBounds(50,275,500,20);
        b=new JButton("Submit");
        b.setBounds(100,450,100,30);
        b.addActionListener(this);
        add(welcome);add(cb1);add(cb2);add(cb3);add(cb4);add(cb5);add(cb6);add(cb7);add(b);add(cb8);add(cb9);
        setSize(600,600);
        setTitle("COVID-19 symptom checklist");
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());
        JTextArea messageView = new JTextArea("Insert form design here");
        messageView.setEditable(false);
        this.add(messageView, BorderLayout.NORTH);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        int amount = 0;
        String message;
        if(cb1.isSelected()){
            amount++;
            cb_1 = "1";
        }
        if(cb2.isSelected()){
            amount++;
            cb_2 = "1";
        }
        if(cb3.isSelected()){
            amount++;
            cb_3 = "1";
        }
        if(cb4.isSelected()){
            amount++;
            cb_4 = "1";
        }
        if(cb5.isSelected()){
            amount++;
            cb_5 = "1";
        }
        if(cb6.isSelected()){
            amount++;
            cb_6 = "1";
        }
        if(cb7.isSelected()){
            amount++;
            cb_7 = "1";
        }
        if(cb8.isSelected()){
            amount++;
            cb_8 = "1";
        }
        if(cb9.isSelected()){
            amount++;
            cb_9 = "1";
        }

        message = cb_1 + cb_2 + cb_3 + cb_4 + cb_5 + cb_6 + cb_7 + cb_8 + cb_9;

        if(amount >= 3) {
            JOptionPane.showMessageDialog(this, "Number of symptoms: " + amount + "\nMessage: " + message);
        }
        else if (amount <= 3){
            JOptionPane.showMessageDialog(this, "Number of symptoms: " + amount + "\nMessage: " + message);
        }
    }
}

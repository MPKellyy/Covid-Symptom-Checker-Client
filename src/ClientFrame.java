import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * This class is responsible controlling which panel is displayed to the user.
 * This class also serves the main frame for the software.
 * Uses observer pattern
 */
public class ClientFrame extends JFrame{
    // Java Swing Attributes
    private JPanel viewSet; // Frame that hold currently viewed form
    private CardLayout cardlayout; // Used to swap out cards/frames in viewSet

    // Client Attributes
    private Socket socket;
    private InputStreamReader input;
    private OutputStreamWriter output;
    private BufferedReader bufferRead;
    private BufferedWriter bufferWrite;

    // Constructor
    /**
     * Constructor for ClientFrame
     */
    public ClientFrame() {
        System.out.println("Client Started\n");

        // Adding a viewable panel to the frame
        viewSet = new JPanel(new CardLayout());
        viewSet.add(new FormPanel(), "message");
        cardlayout = (CardLayout) (viewSet.getLayout());
        cardlayout.show(viewSet, "message");
        this.add(viewSet);

        // Ensuring if user closes window, client is properly shut down
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Client Disconnected");
                disconnect();
            }
        });

        // Creating a submission button
        JButton submissionButton=new JButton("Submit");
        submissionButton.addActionListener(e -> submitForm());
        this.add(submissionButton, BorderLayout.SOUTH);

        // Misc frame settings
        this.setSize(600, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("ClientUI");
        this.setVisible(true);
    }

    // Java Swing Functions
    /**
     * Automatically replaces old viewable frame with a new viewable frame
     */
    private void newForm() {
        viewSet.add(new FormPanel(), "form");
        cardlayout.show(viewSet, "form");
        viewSet.remove(0);
    }

    /**
     * Reads checkboxes within form. Checked = '1', unchecked = '0' per question.
     * @return a binary string containing form answers
     */
    private String parseForm () {
        String binaryResults = "";
        JPanel form = (JPanel)(viewSet.getComponent(0));

        // Selecting the group of checkboxes in form
        for (Component component: form.getComponents()) {
            if (component instanceof Box)
            {
                Box checkboxes = (Box)component;
                // Iterate through each check box in order
                for (int i = 0; i < checkboxes.getComponentCount(); i++) {
                    JCheckBox checkbox = (JCheckBox)checkboxes.getComponent(i);
                    // Update binary result string: 1 for checked, 0 for unchecked
                    if (checkbox.isSelected()) {
                        binaryResults += "1";
                    } else {
                        binaryResults += "0";
                    }
                }
            }
        }

        return binaryResults;
    }

    // Client functions
    /**
     * Connects client to host socket, establishes a read/write buffer, and initializes first panel for frame
     * @param host Host name (i.e. "localhost")
     * @param port Port number (i.e. 1234)
     */
    public void startClient(String host, int port) {
        try {
            // Initializing socket
            socket = new Socket(host, port);

            // Creating character stream reader/writer
            input = new InputStreamReader(socket.getInputStream());
            output = new OutputStreamWriter(socket.getOutputStream());

            // Buffering data from stream to improve read/write efficiency
            bufferRead = new BufferedReader(input);
            bufferWrite = new BufferedWriter(output);

            newForm();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Attempts to send form results to server via a binary string.
     * If server is offline, client is notified and application is closed
     */
    private void submitForm() {
        // Assuming submission button was clicked
        String response = request(parseForm());
        if (response == null) {
            JOptionPane.showMessageDialog(this, "Error: Server disconnected");
            System.out.println("Client Disconnected");
            disconnect();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Submission was successful");
            newForm();
        }
    }

    /**
     * Send server form, receive server response
     * @param message A binary string representing form answers
     * @return Server response. Either string (if connected) or null (if disconnected)
     */
    private String request(String message) {
        String response;
        try {
            // Writing data in buffer
            bufferWrite.write(message);
            bufferWrite.newLine(); // Adding a new line character
            // Ensuring that message is sent when enter is pressed, not when buffer is full (which happens by default)
            bufferWrite.flush();

            // Returning server response
            response = bufferRead.readLine();
        } catch (IOException ex) {
            return null;
        }

        return response;
    }

    /**
     * Ensures client is properly disengaged from server, also closes frame
     */
    public void disconnect() {
        try {
            // Making sure all channels are closed
            if (socket != null) { socket.close(); }
            if (input != null) { input.close(); }
            if (input != null) { output.close(); }
            if (bufferRead != null) { bufferRead.close(); }
            if (bufferRead != null) { bufferWrite.close(); }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Allows client to actively listen server
     */
    public void listen() {
        while (true) {
            // Busy wait logic, infinite loop until client application closes
        }
    }

    /**
     * Initializing client frame
     * @param args
     */
    public static void main(String[] args) {
        try {
            // Starting client services here
            ClientFrame client = new ClientFrame();
            client.startClient("localhost", 1234);
            client.listen();
        }
        catch (Exception e) {
            System.out.println("Unable to connect client. Is server running?");
            System.exit(0);
        }
    }
}

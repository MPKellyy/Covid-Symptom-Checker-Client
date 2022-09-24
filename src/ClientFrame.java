import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


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

    // TODO: Add a submission button for form

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

        // TODO: Add submission button action listener here
            // Assuming submission button was clicked
            // response = request(parseForm());
            // if (response == null)
                // Notify user that server is disconnected (via dialogbox)
                // disconnect()
                // break
            // Else
                // Notify user form submission was successful (via dialogbox)
                // newForm()

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

        // TODO: Implement the following parsing logic once a MVP GUI is made for the survey
        // Note: Not sure if I can iterate through each checkbox sequentially by default, may need
        // to give each one a unique name/ID then sort them out prior to this parsing. This is
        // to ensure that server and client are aware of which questions were true/false.
        // Alternatively, could send over a map with (id, boolean) or (question, boolean)
        // For a MVP, a simple binary string should do.

        // Assuming I can iterate sequentially by default
        // For each check box in frame
        // If checked
        // binaryResults += "1";
        // else
        // binaryResults += "0";

        // This string is for testing purposes only, remove when GUI parsing logic is implemented
        binaryResults = "11010001";

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
        // TODO: Remove the commented out code logic once parsing is implemented in submission button listener
//        Scanner scanner = new Scanner(System.in);
//        String response;
        while (true) {
//            String userInput = scanner.nextLine();
//            response = request(userInput);
//            if (response == null) { break; }
//            System.out.println(response);
        }
//        System.out.println("Server Disconnected");
        // disconnect();
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

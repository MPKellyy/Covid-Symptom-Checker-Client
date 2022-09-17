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
    private JPanel viewSet;
    private CardLayout cardlayout;

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
        // Set up the frame with a few settings.
        viewSet = new JPanel(new CardLayout());
        viewSet.add(new MessagePanel("Client Started"), "message");
        cardlayout = (CardLayout) (viewSet.getLayout());
        cardlayout.show(viewSet, "message");
        this.add(viewSet);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                disconnect();
                System.out.println("Client Disconnected");
            }
        });
        this.setSize(600, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("ClientUI");
        this.setVisible(true);
    }

    // Java Swing Functions
    public void updateText(String message) {
        viewSet.add(new MessagePanel(message), message);
        cardlayout.show(viewSet, message);
        viewSet.remove(0);
    }

    // Client functions
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

            updateText("Client Connected");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String request(String message) {
        String response = null;
        try {
            // Writing data in buffer
            bufferWrite.write(message);
            bufferWrite.newLine(); // Adding a new line character
            // Ensuring that message is sent when enter is pressed, not when buffer is full (which happens by default)
            bufferWrite.flush();

            // Returning server response
            response = bufferRead.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return response;
    }

    public void disconnect() {
        try {
            socket.close();
            input.close();
            output.close();
            bufferRead.close();
            bufferWrite.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listen() {
        Scanner scanner = new Scanner(System.in);
        String response;
        while (true) {
            String userInput = scanner.nextLine();
            response = request(userInput);
            System.out.println(response);
            updateText(response);
        }
    }

    public static void main(String[] args) {
        try {
            // Starting client services here
            ClientFrame client = new ClientFrame();
            client.startClient("localhost", 1234);
            client.listen();
        }
        catch (Exception e) {
            System.out.println("Unable to connect client. Is server running?");
        }
    }
}

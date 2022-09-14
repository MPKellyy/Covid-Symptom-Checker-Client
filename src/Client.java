import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
        private Socket socket;
        private InputStreamReader input;
        private OutputStreamWriter output;
        private BufferedReader bufferRead;
        private BufferedWriter bufferWrite;

        public void startServer(String host, int port) {
            try {
                // Initializing socket
                // Socket socket = socket = new Socket("localhost", 1234);
                Socket socket = socket = new Socket(host, port);

                // Creating character stream reader/writer
                input = new InputStreamReader(socket.getInputStream());
                output = new OutputStreamWriter(socket.getOutputStream());

                // Buffering data from stream to improve read/write efficiency
                bufferRead = new BufferedReader(input);
                bufferWrite = new BufferedWriter(output);
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
}

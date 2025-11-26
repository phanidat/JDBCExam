import java.net.*;
import java.util.Scanner;
import java.io.*;

/**
 * This program opens a connection to a computer specified
 * as the first command-line argument.  If no command-line
 * argument is given, it prompts the user for a computer
 * to connect to.  The connection is made to
 * the port specified by LISTENING_PORT.  The program reads one
 * line of text from the connection and then closes the
 * connection.  It displays the text that it read on
 * standard output.  This program is meant to be used with
 * the server program, DateServer, which sends the current
 * date and time on the computer where the server is running.
 */
public class DataClient {

    public static final int LISTENING_PORT = 32007;

    public static void main(String[] args) {

        String hostName;         // Name of the server computer to connect to.
        Socket connection;       // A socket for communicating with server.
        BufferedReader incoming; // For reading data from the connection.

        /* Get computer name from command line. */

        if (args.length > 0)
            hostName = args[0];
        else {
            Scanner stdin = new Scanner(System.in);
            System.out.print("Enter computer name or IP address: ");
            hostName = stdin.nextLine();
        }

        /* Make the connection, then read and display a line of text. */

        try {
            connection = new Socket( hostName, LISTENING_PORT );
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader user = new BufferedReader(
                    new InputStreamReader(System.in));

            String text;
            while (true) {
                System.out.print("You: ");
                text = user.readLine();
                out.println(text);
                String response = in.readLine();
                System.out.println("Server: "+response);
//                if (text.equalsIgnoreCase("bye")) {
//                    System.out.println("Disconnected.");
//                    break;
//                }
                if(response.equalsIgnoreCase("bye")) {
                    System.out.println("Disconnected.");
                    break;
                }

            }

            connection.close();
//            incoming = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream()) );
//            String lineFromServer = incoming.readLine();
//            if (lineFromServer == null) {
//                // A null from incoming.readLine() indicates that
//                // end-of-stream was encountered.
//                throw new IOException("Connection was opened, " +
//                        "but server did not send any data.");
//            }
//            System.out.println();
//            System.out.println(lineFromServer);
//            System.out.println();
//            incoming.close();



//            boolean receivedAny = false;
//            String lineFromServer;
//
//            while ((lineFromServer = incoming.readLine()) != null) {
//                System.out.println(lineFromServer);
//                receivedAny = true;
//            }
//
//            incoming.close();
//
//            if (!receivedAny) {
//                throw new IOException("Connection was opened, but server did not send any data.");
//            }
        }
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }

    }  // end main()


} //end class DateClient
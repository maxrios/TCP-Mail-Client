import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import jdk.internal.org.jline.utils.InputStreamReader;

public class Client {

    public static void main(String[] args) {
        if (args.length == 1 || args.length > 2) {
            System.out.println("Incorrect use of command: java Client <hostname> <port>");
            return;
        }
        String hostname, port = "";

        if (args.length == 2) {
            hostname = args[0];
            port = args[1];
        }
        else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a mail hostname: ");
            hostname = sc.nextLine();
            System.out.print("Enter a mail port: ");
            port = sc.nextLine();
            sc.close();
        }

        String senderEmail, recipientEmail, subject, message = "";

        System.out.print("Enter your email: ");
        senderEmail = sc.nextLine();
        System.out.print("Enter recipient email: ");
        recipientEmail = sc.nextLine();
        System.out.print("Enter email subject: ");
        subject = sc.nextLine();
        System.out.print("Enter email message: ");
        message = sc.nextLine();
        
        Socket sock = new Socket(hostname, Integer.parseInt(port));
        DataOutputStream outToServer = new DataOutputStream(sock.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        
        String input, response = "";
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Enter 'quit' to disconnect...");
            System.out.println(senderEmail + "@client: ");
            input = sc.nextLine();
            if (input.equals("quit")) {
                break;
            }

            outToServer.writeBytes(input + "\n");
            response = inFromServer.readLine();
            System.out.println(mail + "@server: " + response);
        }
    }
}
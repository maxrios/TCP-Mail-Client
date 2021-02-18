import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

import java.io.InputStreamReader;

public class Client {

    public static void main(String[] args) throws Exception {
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
        Scanner sc = new Scanner(System.in);

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
        
        String input = "";
        
        input = "HELO\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        System.out.println(inFromServer.readLine());

        input = "AUTH LOGIN\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        System.out.println(inFromServer.readLine());
        
        System.out.print("Enter your username: ");
        input = Base64.getEncoder().encodeToString(sc.nextLine().getBytes()) + "\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        System.out.println(inFromServer.readLine());


        System.out.print("Enter your password: ");
        input = Base64.getEncoder().encodeToString(sc.nextLine().getBytes()) + "\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        System.out.println(inFromServer.readLine());

        input = "MAIL FROM: <" + senderEmail + ">\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        System.out.println(inFromServer.readLine());
        

        input = "RCPT TO: <" + recipientEmail + ">\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        System.out.println(inFromServer.readLine());
        

        input = "DATA\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        System.out.println(inFromServer.readLine());

        
        input = "SUBJECT: " + subject + "\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        
        input = "\r\n" + message + "\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        
        input = ".\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        System.out.println(inFromServer.readLine());
        

        input = "QUIT\r\n";
        outToServer.write(input.getBytes("US-ASCII"));
        System.out.println(inFromServer.readLine());
        

        // while(true) {
        //     System.out.println("Enter 'quit' to disconnect...");
        //     System.out.println(senderEmail + "@client: ");
        //     input = sc.nextLine();
        //     if (input.equals("quit")) {
        //         break;
        //     }

        //     outToServer.writeBytes(input + "\n");
        //     response = inFromServer.readLine();
        //     System.out.println("mail@server: " + response);
        // }

        sc.close();
        sock.close();
    }
}
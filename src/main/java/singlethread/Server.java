package singlethread;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {

        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter= null;
        BufferedReader buffReader = null;
        BufferedWriter buffWriter = null;
        ServerSocket serverSocket = null;

        String ipAddress = null;
        String port = null;

        if(args.length != 2){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter ip address for server(enter for localhost): ");
            ipAddress = scanner.nextLine();
            if(ipAddress.isEmpty()){
                ipAddress = "localhost";
            }
            System.out.println("Enter port(enter for 8080):");
            port = scanner.nextLine();
            if(port.isEmpty()){
                port = "8080";
            }
        }else{
            ipAddress = args[0];
            port = args[1];
        }

        try{
            serverSocket = new ServerSocket(Integer.parseInt(port), 50, InetAddress.getByName(ipAddress));
            System.out.println("Server running on port " + serverSocket.getLocalPort()
                    + " of address " + serverSocket.getInetAddress());

        }catch (IOException e){
            System.out.println("Invalid IP address");
            System.exit(1);
        }

        while(true){

            try{
                socket = serverSocket.accept();
                System.out.println("Client connected from "+ socket.getLocalAddress() + " on port " + socket.getPort());
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                buffReader = new BufferedReader(inputStreamReader);
                buffWriter = new BufferedWriter(outputStreamWriter);

                while(true){
                    String messageFromClient = buffReader.readLine();

                    System.out.println("Client ["+ socket.getLocalSocketAddress()+ "]: " + messageFromClient);

                    buffWriter.write("Message recieved");
                    buffWriter.newLine();
                    buffWriter.flush();

                    if(messageFromClient.equalsIgnoreCase("bye")){
                        break;
                    }
                }

                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                buffWriter.close();
                buffReader.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

package multithread;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
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
            System.out.println("Multithreaded Server running on port " + serverSocket.getLocalPort()
                    + " of address " + serverSocket.getInetAddress());

        }catch (IOException e){
            System.out.println("Invalid IP address");
            System.exit(1);
        }

        try {
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress() + " on port " + clientSocket.getPort());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
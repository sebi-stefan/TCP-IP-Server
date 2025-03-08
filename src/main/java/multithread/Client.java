package multithread;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {


        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader buffReader = null;
        BufferedWriter buffWriter = null;

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
            socket = new Socket(ipAddress, Integer.parseInt(port));
            System.out.println("Connected to server on " + socket.getInetAddress() + " on port " + socket.getPort());

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            buffReader = new BufferedReader(inputStreamReader);
            buffWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            while(true){
                String message = scanner.nextLine();
                buffWriter.write(message);

                buffWriter.newLine();
                buffWriter.flush();

                System.out.println("Server: " + buffReader.readLine());

                if(message.equalsIgnoreCase("bye")){
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            try{
                if(socket!=null){
                    socket.close();
                }
                if(inputStreamReader != null){
                    inputStreamReader.close();
                }
                if(outputStreamWriter != null){
                    outputStreamWriter.close();
                }
                if(buffReader != null){
                    buffReader.close();
                }
                if(buffWriter != null){
                    buffWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
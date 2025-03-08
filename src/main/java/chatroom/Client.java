package chatroom;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedWriter buffWriter;
    private BufferedReader buffReader;
    private String username;

    public Client(Socket socket, String username) {

        try{
            this.socket = socket;
            this.buffWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        }catch (IOException e){
            closeEverything(socket, buffReader, buffWriter);
        }

    }

    public void sendMessage(){
        try{
            buffWriter.write(username);
            buffWriter.newLine();
            buffWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected()){
                String messageToSend = scanner.nextLine();
                buffWriter.write(username + ": " + messageToSend);
                buffWriter.newLine();
                buffWriter.flush();
            }
        }catch (IOException e){
            closeEverything(socket, buffReader, buffWriter);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while(socket.isConnected()){
                    try{
                        msgFromGroupChat = buffReader.readLine();
                        System.out.println(msgFromGroupChat);
                    }catch (IOException e){
                        closeEverything(socket, buffReader, buffWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter){
        try{
            if(socket != null){
            socket.close();
        }
            if(buffReader != null){
                buffReader.close();
            }
            if(buffWriter != null){
                buffWriter.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the groupchat");
        String username = scanner.nextLine();

        Socket socket = new Socket("localhost", 8080);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}

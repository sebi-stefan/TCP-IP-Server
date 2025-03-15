package multithread;

import java.io.*;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader buffReader;
    private BufferedWriter buffWriter;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            this.buffReader = new BufferedReader(inputStreamReader);
            this.buffWriter = new BufferedWriter(outputStreamWriter);
        } catch (IOException e) {
            System.out.println("Error in ClientHandler constructor: " + e.getMessage());
            closeEverything();
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                String messageFromClient = buffReader.readLine();
                if (messageFromClient == null) {
                    break;
                }

                System.out.println("Client [" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + "]: " + messageFromClient);

                buffWriter.write("Recieved Message: " + messageFromClient);
                buffWriter.newLine();
                buffWriter.flush();

                if(messageFromClient.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error in client handler: " + e.getMessage());
        } finally {
            closeEverything();
        }
    }

    private void closeEverything() {
        try {
            if (buffReader != null) {
                buffReader.close();
            }
            if (buffWriter != null) {
                buffWriter.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                System.out.println("Client disconnected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
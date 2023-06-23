
package main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class EchoServer {
    public int port;
    protected ArrayList<EchoThread> threads;
    public ArrayDeque<String> messages;
    
    public EchoServer(int port) {
        this.port = port;
        threads = new ArrayList<>();
        messages = new ArrayDeque<>();
    }
    
    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Server could not bind to port: " + e);
        }
        
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Client connected!");
            } catch (Exception e) {
                System.out.println("Connection error: " + e);
            }
            //new EchoThread(socket, this).start();
            EchoThread thread = new EchoThread(socket, this, threads.size());
            thread.start();
            threads.add(thread);
        }
    }
    
    public void send() {
        String message;
        while(messages.peekFirst() != null) {
            message = messages.pollFirst();
            System.out.println(message);
            for (EchoThread thread : threads) {
                try {
                    thread.out.writeBytes(message + "\n\r");
                    thread.out.flush();
                } catch(Exception e) {
                    System.out.println("Could not send message.");
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void setPositions() {
        int i = 0;
        for (EchoThread thread : threads) {
            thread.position = i;
            i++;
        }
    }
    
}


package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class EchoThread extends Thread {
    protected Socket socket;
    protected InputStream inp;
    protected BufferedReader brinp;
    public DataOutputStream out;
    protected EchoServer server;
    protected int position;
    
    public EchoThread(Socket clientSocket, EchoServer server, int position) {
        this.socket = clientSocket;
        inp = null;
        brinp = null;
        out = null;
        this.server = server;
        this.position = position;
    }
    
    @Override
    public void run() {
        inp = null;
        brinp = null;
        out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch(Exception e) {
            System.out.println("Streaming error!");
        }
        String line;
        while(true) {
            try {
                line = brinp.readLine();
                if((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    server.threads.remove(position);
                    server.setPositions();
                    return;
                } else {
                    //System.out.println(line);
                    server.messages.add(line);
                    server.send();
                    //out.writeBytes(line + "\n\r");
                    //out.flush();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}

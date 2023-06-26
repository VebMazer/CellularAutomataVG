
package vm.emergencevg.control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;

import vm.emergencevg.logic.Environment;

public class Communicator {
    public  Environment        environment;
    public  ArrayDeque<String> outCommands;
    
    private Socket             echoSocket;
    public  DataOutputStream   out;
    public  BufferedReader     in;
    
    public  int                groupID;
    public  boolean            sending;
    
    public Communicator(Environment environment) {
        this.environment = environment;
        outCommands = new ArrayDeque<>();
        groupID = 0;
        sending = true;
    }
    
    public void connect(String hostName, int portNumber) {
        try {
            echoSocket = new Socket(hostName, portNumber);
            out =
                new DataOutputStream(echoSocket.getOutputStream());
            in = new BufferedReader(
                new InputStreamReader(echoSocket.getInputStream()));

        } catch(Exception e) {
            System.out.println("Could not bind to port.");
        }
    }
    
    public void readCommands() {
        if(!sending) {
            try {
                //while(read(in.readLine()));
                //read(in.readLine());
                while(in.ready() && read(in.readLine()));
            } catch(Exception e) {
                System.out.println("Error reading commands.");
                e.printStackTrace();
            }
        }
    }
    
    public boolean read(String line) {
        if(line == null) return false;
        else {
            System.out.println(line);
            environment.functions.execCommand(line);
        }
        return true;
    }
    
    public void addOutputCommand(String command) {
        if(sending) outCommands.add(command);
    }
    
    public void sendCommands() {
        if(sending) {
            try {
                while(!outCommands.isEmpty()) {
                    //out.write(outCommands.pollFirst());
                    out.writeBytes(outCommands.pollFirst() + "\n\r");
                }
            } catch(Exception e) {
                //System.out.println("Error sending commands.");
            }
        }
    }
}

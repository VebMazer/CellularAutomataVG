
package vm.emergencevg.control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import vm.emergencevg.logic.GenerativeSpace;

public class Communicator {
    public GenerativeSpace space;
    public ArrayDeque<String> outCommands;
//    public PrintWriter out;
    public DataOutputStream out;
    BufferedReader in;
    public int groupID;
    public boolean sending;
    
    public Communicator(GenerativeSpace space) {
        this.space = space;
        outCommands = new ArrayDeque<>();
        groupID = 0;
        sending = true;
    }
    
    public void connect(String hostName, int portNumber) {
        try {
            Socket echoSocket = new Socket(hostName, portNumber);
//            out = 
//                new PrintWriter(echoSocket.getOutputStream(), true);
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
            space.functions.execCommand(line);
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

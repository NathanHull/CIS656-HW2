package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ProcessIncomingRequest implements Runnable {
    private Socket receiveSocket;

    public ProcessIncomingRequest(Socket receiveSocket) {
        super();
        this.receiveSocket = receiveSocket;
    }

    @Override
    public void run() {
        String line;
        BufferedReader is;

        try {
            is = new BufferedReader(new InputStreamReader(receiveSocket.getInputStream()));

            while(true) {
                line = is.readLine();
                if(line == null) {
                    break;
                }
                System.out.println("Received: " + line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

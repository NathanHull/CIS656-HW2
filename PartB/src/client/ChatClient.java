package client;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.Scanner;
import java.util.Vector;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.net.Socket;
import java.io.*;

import compute.*;


public class ChatClient {
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {

            if (args.length < 1 || args.length > 2) {
                System.out.println("Invalid input");
                System.out.println("Usage: ChatClient (userName) [host[:port]]");
                System.exit(1);
            }

            String registryName = "Chat";
            String userName = args[0];
            String host = "localhost";
            int port = 1099;

            // If they exist, get program args
            if (args.length > 1) {
                String passedHost = args[1];
                String[] splitHost = passedHost.split(":");
                if (splitHost.length > 1) {
                    host = splitHost[0];
                    port = Integer.parseInt(splitHost[1]);
                }
                else {
                    host = passedHost;
                }
            }

            RegistrationInfo reg = new RegistrationInfo(userName, host, port, true);

            Registry registry = LocateRegistry.getRegistry(host, port);
            PresenceService service = (PresenceService) registry.lookup(registryName);
            service.register(reg);

            Scanner scanner = new Scanner(System.in);
            System.out.println();
            
            while (true) {

                System.out.println("\n----------\n0: friends\n1: talk\n2: broadcast\n3: busy\n4: available\n5: exit");
                int userChoice = 6;
                try {
                    userChoice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input");
                    scanner.nextLine();
                }
                
                System.out.println();

                switch (userChoice) {
                    case 0:
                        System.out.println("Friends:");
                        System.out.println(service.listRegisteredUsers());
                        break;
                    case 1:
                        System.out.println("Talk: to who?");
                        scanner.nextLine();
                        String targetUserName = scanner.nextLine();
                        RegistrationInfo targetRI = service.lookup(targetUserName);

                        if (targetRI == null) {
                            System.out.println("User does not exist");
                        } else if (targetRI.getStatus()) {
                            String message = scanner.nextLine();

                            Socket socket = new Socket(targetRI.getHost(), targetRI.getPort());
                        new PrintStream(socket.getOutputStream()).println(message);
                            socket.close();
                        }
                        break;
                    case 2:
                        System.out.println("Broadcast: message?");
                        scanner.nextLine();
                        String message = scanner.nextLine();

                        Vector<RegistrationInfo> targetRIs = service.listRegisteredUsers();
                        for (RegistrationInfo RI : targetRIs) {
                            if (RI.getStatus()) {
                                Socket socket = new Socket(RI.getHost(), RI.getPort());
                                new PrintStream(socket.getOutputStream()).println(message);
                                socket.close();
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Setting status as busy");
                        service.updateRegistrationInfo(new RegistrationInfo(userName, host, port, false));
                        break;
                    case 4:
                        System.out.println("Setting status as available");
                        service.updateRegistrationInfo(new RegistrationInfo(userName, host, port, true));
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Chat Client exception:");
            e.printStackTrace();
        }
    }
}
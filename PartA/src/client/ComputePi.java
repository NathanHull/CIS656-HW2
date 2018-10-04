package client;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.ArrayList;
import compute.Compute;

public class ComputePi {
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            Compute comp = (Compute) registry.lookup(name);

            Scanner scanner = new Scanner(System.in);
            System.out.println();
            
            while (true) {
                System.out.println("\n1: Compute Pi\n2: Compute Primes\n3: Exit");
                int userChoice = scanner.nextInt();
                if (userChoice == 1) {
                    System.out.print("Compute Pi\nEnter number of digits: ");
                    Pi task = new Pi(scanner.nextInt());
                    BigDecimal pi = comp.executeTask(task);
                    System.out.println(pi);
                }
                else if (userChoice == 2) {
                    System.out.print("Compute Primes\nEnter Min: ");
                    int min = scanner.nextInt();
                    System.out.print("Enter Max: ");
                    int max = scanner.nextInt();
                    Primes task = new Primes(min, max);
                    ArrayList<Integer> primes = comp.executeTask(task);
                    System.out.println(primes);
                } else if (userChoice == 3) {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}
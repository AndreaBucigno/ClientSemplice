import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSemplice {
    public static void  main(String[] args){
        final String hostname = "127.0.0.1";
        int port = 12345;
        try (Socket socket = new Socket(hostname, port)){
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            System.out.println("=== CALCOLATORE POLIGONI REGOLARI ===");
            System.out.println("Connesso al server.");
            System.out.println("Inserisci: numeroLati lunghezzaLato");
            System.out.println("Esempio: 5 10 (per un pentagono con lato di 10)");
            System.out.println("Scrivi 'BYE' per uscire\n");
            
            String userInput;
            while (scanner.hasNextLine()) {
                System.out.print("Input: ");
                userInput = scanner.nextLine();
                
                out.println(userInput);
                
                if ("BYE".equalsIgnoreCase(userInput)) {
                    System.out.println(in.readLine());
                    break;
                }
                
                String response = in.readLine();
                System.out.println(response + "\n");
            }
            
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

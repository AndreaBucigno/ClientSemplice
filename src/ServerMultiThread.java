import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMultiThread {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        
        System.out.println("=== SERVER CALCOLATORE POLIGONI ===");
        System.out.println("Server avviato sulla porta " + port);
        System.out.println("In attesa di connessioni...\n");

        while (true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("✓ Nuovo client connesso: " + clientSocket.getInetAddress());

            ClientHandler clientHandler = new ClientHandler(clientSocket);
            Thread thread = new Thread(clientHandler);
            thread.start();
            
            System.out.println("  Thread avviato per client: " + clientSocket.getInetAddress());
        }
    }
}

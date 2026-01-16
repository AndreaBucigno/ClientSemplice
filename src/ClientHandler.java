import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(),true);){
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("BYE".equalsIgnoreCase(inputLine)) {
                    out.println("Arrivederci!");
                    break;
                }
                
                // Elabora il calcolo del poligono
                String response = calcolaPoligono(inputLine);
                out.println(response);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{ socket.close();}catch (IOException e){ System.out.println(e.toString()); }
        }
    }
    
    private String calcolaPoligono(String input) {
        try {
            // Il formato atteso è: "numeroLati lunghezzaLato"
            String[] parti = input.trim().split("\\s+");
            
            if (parti.length != 2) {
                return "ERRORE: Formato non valido. Usa: numeroLati lunghezzaLato";
            }
            
            int numeroLati = Integer.parseInt(parti[0]);
            double lunghezzaLato = Double.parseDouble(parti[1]);
            
            if (numeroLati < 3) {
                return "ERRORE: Un poligono deve avere almeno 3 lati";
            }
            
            if (lunghezzaLato <= 0) {
                return "ERRORE: La lunghezza del lato deve essere positiva";
            }
            
            // Calcola il perimetro
            double perimetro = numeroLati * lunghezzaLato;
            
            // Calcola l'area di un poligono regolare
            // Formula: Area = (n * l^2) / (4 * tan(π/n))
            double area = (numeroLati * lunghezzaLato * lunghezzaLato) / 
                         (4 * Math.tan(Math.PI / numeroLati));
            
            return String.format("RISULTATO - Lati: %d, Lunghezza: %.2f | Perimetro: %.2f | Area: %.2f", 
                               numeroLati, lunghezzaLato, perimetro, area);
            
        } catch (NumberFormatException e) {
            return "ERRORE: I valori devono essere numerici";
        } catch (Exception e) {
            return "ERRORE: " + e.getMessage();
        }
    }
}

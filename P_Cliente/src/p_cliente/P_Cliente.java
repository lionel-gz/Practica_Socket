package p_cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.time.LocalTime;

public class P_Cliente{
    
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);

        Socket socket = new Socket("localhost", 5000);

        BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        PrintWriter salida = new PrintWriter(
                socket.getOutputStream(), true
        );

        new Thread(() -> {
            try {
                String respuesta;
                while ((respuesta = entrada.readLine()) != null) {
                    System.out.println(respuesta);
                }
            } catch (IOException e) {
                System.out.println("Conexión cerrada");
            }
        }).start();

        
        while (true) {
            
            System.out.print(">");
            String mensaje = s.nextLine();

            salida.println(mensaje);

            if (mensaje.equalsIgnoreCase("salir")) {
                break;
            }
        }
        
        
    }
}
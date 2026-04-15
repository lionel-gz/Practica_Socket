
package p_cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.time.LocalTime;

public class P_Cliente{
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            PrintWriter salida = new PrintWriter(
                socket.getOutputStream(), true
            );

            Scanner s = new Scanner(System.in);

            String mensaje;

            while (true) {
                System.out.print("Escribí un mensaje: ");
                mensaje = s.nextLine();

                salida.println(mensaje);

                String respuesta = entrada.readLine();
                System.out.println("Servidor: " + respuesta);

                if (mensaje.equalsIgnoreCase("salir")) {
                    break;
                }
            }

            socket.close();
            System.out.println("Cliente desconectado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
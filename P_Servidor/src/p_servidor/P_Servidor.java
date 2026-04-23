package p_servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class P_Servidor {

    static List<ClienteHilo> clientes = new CopyOnWriteArrayList<>();
    
    public static void main(String[] args) throws IOException {
        
        
        
        try{
            
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Servidor iniciado.....");
            
            
            while(true){
                    
                Socket socket = servidor.accept();
                System.out.println("Nuevo cliente conectado");
                
                ClienteHilo a = new ClienteHilo(socket);
                new Thread(a).start();
                
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
            
        }
        
        
    }
}

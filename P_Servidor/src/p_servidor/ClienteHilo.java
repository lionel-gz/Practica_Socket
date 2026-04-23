package p_servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class ClienteHilo implements Runnable{

    private Socket socket;
    
    BufferedReader entrada;
    PrintWriter salida;
    String nombre;

    
    public ClienteHilo(Socket socket) {
            this.socket = socket;
    }
    
    
    public boolean verificarNombre(String nombre){
        
        for(ClienteHilo c : P_Servidor.clientes){
            
            if(c != this && c.nombre != null && c.nombre.equals(nombre)){
                
                return true;
            }
        }
        
        return false;
    }
    
    
    
    
    
    @Override
    public void run() {
        
        try{
            
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(),true);
            
            P_Servidor.clientes.add(this);
            
            salida.println("Ingrese su nombre :");
            nombre = entrada.readLine();
            
            System.out.println("Cliente conectado :" + nombre);
            
            String z = nombre;
            int i = 1;
            while(verificarNombre(nombre)){
                
                nombre = z + i;
                i++;
            }
            
            for (ClienteHilo c : P_Servidor.clientes) {
                
                if (c != this && c.salida != null) {
                    c.salida.println("> " + nombre + " se conecto");
                }
                
            }
            
            
            salida.println("-----------------------");
            salida.println("Bienvenido "+ nombre);
            salida.println("Comandos disponibles");
            salida.println("ALL mensaje");
            salida.println("PRIV usuario mensaje");
            salida.println("CLIENTES");
            salida.println("FECHA");
            salida.println("RESOLVE cuenta");
            salida.println("PAL palabra");
            salida.println("REV palabra");
            salida.println("PAR numero");
            salida.println("SALIR");
            salida.println("-----------------------");
            
            String mensaje;
            
             while((mensaje = entrada.readLine()) != null){
                 
                 System.out.println("["+ nombre +"] -> " + mensaje);
                 
                 if (mensaje.toUpperCase().startsWith("ALL")) {
                     
                     String t = mensaje.length() > 3 ? mensaje.substring(3).trim() : "";

                     for (ClienteHilo c : P_Servidor.clientes) {
                         if (c.salida != null) {
                             c.salida.println("[" + nombre + " -> TODOS]: " + t);
                         }
                     }
                     
                 } else if (mensaje.toUpperCase().startsWith("SALIR")) {
                     
                     for (ClienteHilo c : P_Servidor.clientes) {
                         
                         if (c != this && c.salida != null) {
                             
                             c.salida.println("> " + nombre + " se desconecto");
                         }
                     }

                     
                     salida.println("Conexión cerrada");
                     P_Servidor.clientes.remove(this);
                     break;
                     
                     
                 } else if (mensaje.toUpperCase().startsWith("CLIENTES")) {
                     
                     String listaClientes  = "";
                     
                     for(ClienteHilo l: P_Servidor.clientes){
                         listaClientes +=  l.nombre + " // ";
                     }
                     
                     salida.println("[Listado de Clientes]: "+listaClientes);
                     
                 }else if (mensaje.toUpperCase().startsWith("PRIV")){
                     
                     String [] p = mensaje.split(" ",3);
                     
                     if(p.length < 3){
                         
                         salida.println("Uso del comando incorrecto");
                         continue;
                     }
                     
                     boolean esta = false;
                     
                     String c_destino = p[1];
                     String ms = p[2];
                     
                     for(ClienteHilo q : P_Servidor.clientes){
                         if(q.nombre.equalsIgnoreCase(c_destino)){
                             q.salida.println("[Privado de "+nombre+ "] : "+ms);
                             esta = true;
                         }
                     }
                     
                     if(!esta){
                         salida.println("El usuario no se a encontrado");
                     }
                     
                 }else if(mensaje.toUpperCase().startsWith("RESOLVE")){
                     
                     try {
                        String cuenta = mensaje.substring(8).trim();

                        ScriptEngineManager manager = new ScriptEngineManager();
                        ScriptEngine engine = manager.getEngineByName("JavaScript");

                        Object resultado = engine.eval(cuenta);

                        salida.println("Resultado: " + resultado);

                    } catch (Exception e) {
                        salida.println("Error al resolver la expresión");
                    }
                     
                }else if(mensaje.toUpperCase().startsWith("FECHA")){
                    
                    try {
                         LocalDateTime ahora = LocalDateTime.now();
                        salida.println("Fecha actual: " + ahora);
                    } catch (Exception e) {
                        salida.println("Error al obtener la hora");
                    }
                
                }else if(mensaje.toUpperCase().startsWith("PAR")){
                    
                    try {
                        int numero = Integer.parseInt(mensaje.substring(4).trim());

                        if (numero % 2 == 0) {
                            salida.println("Resultado: Es par");
                        } else {
                            salida.println("Resultado: Es impar");
                        }

                    } catch (Exception e) {
                        salida.println("Error: ingresá un número válido");
                    }
                
                }else if(mensaje.toUpperCase().startsWith("REV")){
                    
                    try {
                        String texto = mensaje.substring(4).trim();
                        String inv = "";

                        for (int k = texto.length() - 1; k >= 0; k--) {
                            inv += texto.charAt(k);
                        }

                        salida.println("Resultado: " + inv);

                    } catch (Exception e) {
                        salida.println("Error al procesar");
                    }
                
                }else if(mensaje.toUpperCase().startsWith("PAL")){
                    
                    try {
                        String texto = mensaje.substring(4).trim();

                        String aux = "";

                        for (int j = texto.length() - 1; j >= 0; j--) {
                            aux = aux + texto.charAt(j);
                        }

                        if (texto.equalsIgnoreCase(aux)) {
                            salida.println("Resultado: Es PALINDROMO");
                        } else {
                            salida.println("Resultado: No es palindromo");
                        }

                    } catch (Exception e) {
                        salida.println("Error al resolver");
                    }
                
                }else{
                     
                     salida.println("[" + nombre + "] :" + mensaje);     
                }
      
            }
            
            
        }catch (IOException e){
            e.printStackTrace();
        }
        
    }
    
}

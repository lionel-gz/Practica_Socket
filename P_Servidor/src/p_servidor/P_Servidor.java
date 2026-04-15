
// Clave Segura
// Dia
// Palindromo


package p_servidor;


import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.time.LocalTime;

public class P_Servidor {

    public static void main(String[] args) {
        
        try {
            
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Servidor esperando cliente...");

            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado");

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(cliente.getInputStream())
            );

            PrintWriter salida = new PrintWriter(
                    cliente.getOutputStream(), true
            );

            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {

                // LOG en servidor
                System.out.println("Cliente: " + mensaje);

                // SALIR
                if (mensaje.equalsIgnoreCase("salir")) {
                    salida.println("Conexión cerrada");
                    break;
                }

                /*
                // COMANDO RESOLVE
                if (mensaje.startsWith("RESOLVE")) {
                    try {
                        String cuenta = mensaje.substring(8).trim();

                        ScriptEngineManager manager = new ScriptEngineManager();
                        ScriptEngine engine = manager.getEngineByName("JavaScript");

                        Object resultado = engine.eval(cuenta);

                        salida.println("Resultado: " + resultado);

                    } catch (Exception e) {
                        salida.println("Error al resolver la expresión");
                    }
                } else {
                    // RESPUESTA NORMAL
                    salida.println("Servidor recibió: " + mensaje);
                }*/
                if (mensaje.startsWith("PAL")) { //PALINDROMO

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

                } else if (mensaje.startsWith("FECHA")) { // 

                    try {
                         LocalDateTime ahora = LocalDateTime.now();
                        salida.println("Fecha actual: " + ahora);
                    } catch (Exception e) {
                        salida.println("Error al obtener la hora");
                    }

                } else if (mensaje.startsWith("MAYUS")) { // Pasa a mayuscula

                    try {
                        String texto = mensaje.substring(6).trim();
                        salida.println("Resultado: " + texto.toUpperCase());
                    } catch (Exception e) {
                        salida.println("Error al procesar");
                    }

                } else if (mensaje.startsWith("MINUS")) { // Pasa a minuscula

                    try {
                        String texto = mensaje.substring(6).trim();
                        salida.println("Resultado: " + texto.toLowerCase());
                    } catch (Exception e) {
                        salida.println("Error al procesar");
                    }

                } else if(mensaje.startsWith("REV")){ //Invierte el texto que se le ingresa
                    try {
                        String texto = mensaje.substring(4).trim();
                        String inv = "";

                        for (int i = texto.length() - 1; i >= 0; i--) {
                            inv += texto.charAt(i);
                        }

                        salida.println("Resultado: " + inv);

                    } catch (Exception e) {
                        salida.println("Error al procesar");
                    }
                    
                }else if(mensaje.startsWith("PAR")){ //Par o impar
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

        
                }else {
                    salida.println("Servidor recibió: " + mensaje);
                }

            }

            cliente.close();
            
            servidor.close();
            System.out.println("Servidor cerrado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

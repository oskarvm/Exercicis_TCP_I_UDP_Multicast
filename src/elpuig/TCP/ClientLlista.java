package elpuig.TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientLlista extends Thread {
    /* Client TCP que ha endevinar un número pensat per SrvTcpAdivina.java */

    String hostname;
    int port;
    boolean ordenat;
    Llista server;
    List<Integer> numeros = new ArrayList<>();
    Llista respuesta = new Llista("Oscar",numeros);

    public ClientLlista(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;

    }

    public void run() {

        numeros.add(7);
        numeros.add(7);
        numeros.add(2);
        numeros.add(9);
        numeros.add(5);
        numeros.add(10);
        numeros.add(5);

        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;

        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            while (!ordenat) {


            }
            close(socket);
        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de connexió indefinit: " + ex.getMessage());
        }

    }


    private void close(Socket socket){
        //si falla el tancament no podem fer gaire cosa, només enregistrar
        //el problema
        try {
            //tancament de tots els recursos
            if(socket!=null && !socket.isClosed()){
                if(!socket.isInputShutdown()){
                    socket.shutdownInput();
                }
                if(!socket.isOutputShutdown()){
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            //enregistrem l'error amb un objecte Logger
            Logger.getLogger(ClientLlista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
		/*if (args.length != 2) {
            System.err.println(
                "Usage: java ClientTcpAdivina <host name> <port number>");
            System.exit(1);
        }*/

        // String hostName = args[0];
        // int portNumber = Integer.parseInt(args[1]);
        ClientLlista clientLlista = new ClientLlista("localhost",5558);
        clientLlista.start();
    }
}
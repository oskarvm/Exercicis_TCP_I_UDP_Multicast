package elpuig.TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientLlista extends Thread {
    String hostname;
    int puerto;
    boolean continuar;

    public ClientLlista(String hostname, int puerto) {
        this.hostname = hostname;
        this.puerto = puerto;
        continuar = true;
    }

    public void run() {
        List<Integer> serverData;

        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;

        try {
            socket = new Socket(InetAddress.getByName(hostname), puerto);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            while(continuar){
                List<Integer> numeros = new ArrayList<>();
                numeros.add(5);
                numeros.add(2);
                numeros.add(7);
                numeros.add(9);
                numeros.add(2);
                numeros.add(9);

                out.writeObject(numeros);
                out.flush();

                serverData = (List<Integer>) in.readObject();
                getRequest(serverData);
            }
            close(socket);

        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de connexió indefinit: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void getRequest(List<Integer> serverData) {
        System.out.println(serverData.toString());
        continuar = false;
    }

    private void close(Socket socket){
        try {
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
            Logger.getLogger(ClientLlista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        ClientLlista clientTcp = new ClientLlista("localhost",5558);
        clientTcp.start();
    }
}
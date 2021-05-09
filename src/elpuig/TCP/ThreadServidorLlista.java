package elpuig.TCP;

import java.io.*;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

public class ThreadServidorLlista implements Runnable {
    Socket clientSocket = null;
    ObjectInputStream input;
    ObjectOutputStream outpput;
    List<Integer> msgEntrant, msgSortint;
    boolean ordenado;

    public ThreadServidorLlista(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        ordenado = false;
        outpput = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while(!ordenado) {
                msgEntrant = (List<Integer>) input.readObject();

                msgSortint = ordenarNumeros(msgEntrant);

                outpput.writeObject(msgSortint);
                outpput.flush();
            }
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getLocalizedMessage());
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> ordenarNumeros(List<Integer> ordenar) {
        List<Integer> numerosOrdenados;
        Collections.sort(ordenar);
        numerosOrdenados = ordenar;
        return numerosOrdenados;
    }

}

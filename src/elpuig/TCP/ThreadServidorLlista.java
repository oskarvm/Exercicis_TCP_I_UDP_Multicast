package elpuig.TCP;

import java.io.*;
import java.net.Socket;

public class ThreadServidorLlista implements Runnable {
    /* Thread que gestiona la comunicació de SrvTcPAdivina.java i un cllient ClientTcpAdivina.java */

    Socket clientSocket = null;
    ObjectInputStream input = null;
    ObjectOutputStream output = null;

    public ThreadServidorLlista(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        Llista ll = null;
        try {
            ll = (Llista) input.readObject();
            //ordenar
            output.writeObject(ll);
            output.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

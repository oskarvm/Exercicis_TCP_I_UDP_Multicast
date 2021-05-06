package elpuig.TCP;
import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.logging.Level;
        import java.util.logging.Logger;


public class ServidorLLista {
    /* Servidor TCP que genera un número perquè ClientTcpAdivina.java jugui a encertar-lo
     * i on la comunicació dels diferents jugador passa per el Thread : ThreadServidorAdivina.java
     * */
    int port;
    //NombreSecret ns;

    public ServidorLLista(int port ) {
        this.port = port;
       // ns = new NombreSecret(100);
    }

    public void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                //Llançar Thread per establir la comunicació
                ThreadServidorLlista FilServidor = new ThreadServidorLlista(clientSocket/*, ns*/);
                Thread client = new Thread(FilServidor);
                client.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorLLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
		/*if (args.length != 1) {
            System.err.println("Usage: java SrvTcpAdivina <port number>");
            System.exit(1);
        }*/


        //int port = Integer.parseInt(args[0]);
        ServidorLLista srv = new ServidorLLista(5558);
        srv.listen();

    }

}
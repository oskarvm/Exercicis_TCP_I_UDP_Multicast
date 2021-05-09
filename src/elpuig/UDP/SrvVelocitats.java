package elpuig.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class SrvVelocitats {

    MulticastSocket multicastSocket;
    InetAddress inetAddress;
    int puerto;
    boolean continuar = true;
    Velocitat velocitat;

    public SrvVelocitats(int portValue, String strIp) throws IOException {
        multicastSocket = new MulticastSocket(portValue);
        inetAddress = InetAddress.getByName(strIp);
        puerto = portValue;
        velocitat = new Velocitat(100);
    }

    public void runServer() throws IOException{
        DatagramPacket datagramPacket;
        byte [] sendingData;

        while(continuar){
            sendingData = ByteBuffer.allocate(4).putInt(velocitat.agafaVelocitat()).array();
            datagramPacket = new DatagramPacket(sendingData, sendingData.length, inetAddress, puerto);
            multicastSocket.send(datagramPacket);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }

        }
        multicastSocket.close();
    }

    public static void main(String[] args) throws IOException {
        SrvVelocitats srvVel = new SrvVelocitats(5557, "224.0.2.12");
        srvVel.runServer();
        System.out.println("Se acabo");

    }

}

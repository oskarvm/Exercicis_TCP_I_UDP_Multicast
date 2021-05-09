package elpuig.UDP;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

public class ClientVelocimetre {

    MulticastSocket multisocket;
    InetAddress inetAddress;
    int puerto;
    List<Integer> integerList = new ArrayList<>();

    public void init(String strIp, int portValue) throws SocketException,IOException {
        inetAddress = InetAddress.getByName(strIp);
        puerto = portValue;
        multisocket = new MulticastSocket(puerto);
    }

    public void runClient() throws IOException{
        DatagramPacket datagramPacket;
        byte [] receivedData = new byte[1024];
        boolean continuar = true;

        multisocket.joinGroup(inetAddress);

        while(continuar){
            datagramPacket = new DatagramPacket(receivedData, 1024);
            multisocket.setSoTimeout(5000);
            try{
                multisocket.receive(datagramPacket);
                calcularMedia(datagramPacket.getData(), datagramPacket.getLength());
            }catch(SocketTimeoutException e){
                e.printStackTrace();
            }
        }
        multisocket.leaveGroup(inetAddress);
    }

    private void calcularMedia(byte[] data, int length) {
        int s = ByteBuffer.wrap(data).getInt();

        integerList.add(s);
        if (integerList.size() == 5){
            int m = integerList.get(0) +integerList.get(1) +integerList.get(2) +integerList.get(3) +integerList.get(4) / 5;
            System.out.println("La media de la velocidad es de: " + m);
            integerList.clear();
        }
    }

    public static void main(String[] args) {
        ClientVelocimetre c = new ClientVelocimetre();
        try {
            c.init("224.0.10.54",5557);
            c.runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

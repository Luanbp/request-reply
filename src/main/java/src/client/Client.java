package src.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] var0) throws Exception {

        BufferedReader var1 = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket var2 = new DatagramSocket();
        InetAddress var3 = InetAddress.getByName("localhost");

        byte[] var4;
        byte[] var5 = new byte[1024];

        System.out.println("Escreva algo...");
        String var6 = var1.readLine();
        var4 = var6.getBytes();

        DatagramPacket var7 = new DatagramPacket(var4, var4.length, var3, 9876);
        var2.send(var7); //Enviando o pacote com a mensagem

        DatagramPacket var8 = new DatagramPacket(var5, var5.length);
        var2.receive(var8); //Recebendo a mensagem do servidor

        String var9 = new String(var8.getData());

        System.out.println("Resposta do servidor:" + var9);
        var2.close(); //Fechando comunicação
    }
}

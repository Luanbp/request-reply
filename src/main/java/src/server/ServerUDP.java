package src.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

@Data
@AllArgsConstructor
public class ServerUDP implements Runnable {

    private final Logger log = Logger.getLogger(ServerUDP.class.getName());

    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private byte[] receiveData;
    private byte[] sendData;
    private InetAddress inetAddressClient;
    private int portClient;

    {
        // Definindo o tamanho da mensagem que pode ser recebida.
        receiveData = new byte[1024];
        sendData = new byte[1024];
    }

    public ServerUDP() throws SocketException {
        this.serverSocket = new DatagramSocket(9876);
        this.receivePacket = new DatagramPacket(receiveData, receiveData.length);
    }

    public ServerUDP(InetAddress inetAddressClient, int portClient, byte[] receiveData, DatagramSocket serverSocket) throws SocketException {
        this.serverSocket = serverSocket;
        this.inetAddressClient = inetAddressClient;
        this.portClient = portClient;
        this.receiveData = receiveData;
    }

    public void receive() throws IOException {

        this.serverSocket.receive(this.receivePacket);
        log.info("[SERVER] Recebendo conexão.");

        String msg = new String(this.receivePacket.getData());
        log.info("[SERVER] Mensagem recebida: " + msg);

        new Thread(new ServerUDP(
                this.receivePacket.getAddress(),
                this.receivePacket.getPort(),
                this.receivePacket.getData(),
                this.serverSocket))
                .start();
    }


    public void run() {
        log.info("Iniciando nova Thread");
        String sentence = new String(this.receiveData);
        InetAddress IPAddress = this.inetAddressClient; //Definindo a porta para comunicação

        String capitalizedSentence = sentence.toUpperCase();
        sendData = capitalizedSentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portClient); //Preparando pacote para envio

        try {
            serverSocket.send(sendPacket); //Enviando
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

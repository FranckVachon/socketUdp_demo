package server;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import coms.Messge;
import coms.Utils;

public class SocketUDP extends Thread {
	/*Simple pair client/server en UDP. LE server (qui n'Est pour l'instant pas plus qu'un socket)
	 * écoute sur Utils.port (8000 par défaut).
	 * */
	
	protected int port = Utils.port;
    protected DatagramSocket udpsocket = null;
    protected BufferedReader in = null;

    public SocketUDP() throws IOException {
	this("simpleudpserver");
    }

    public SocketUDP(String name) throws IOException {
        super(name);
        udpsocket = new DatagramSocket(port);
        System.out.println("server:" + super.getName());

    }

    public void run() {
    	

        while (true) {
            try {
                byte[] buf = new byte[Utils.totalBytes];
                
            	//recevoir un paquet - peut être n'importe qui
                DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
                udpsocket.receive(inPacket);
                
                //Constructeur de Messge prend les byte[] et construit un truck util avec
                Messge msg = new Messge(inPacket.getData());
                
                //DEBUG: juste pour montrer qu'on maîtrise les données
    			System.out.println("Msg de client:" + msg.toString());
                buf = msg.toBytes();
                
                // renvoyer une réponse au client - la même chose qu'il nous a envoyé
                InetAddress address = inPacket.getAddress();
                int port = inPacket.getPort();
                DatagramPacket outPacket = new DatagramPacket(buf, buf.length, address, port);
                udpsocket.send(outPacket);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

}
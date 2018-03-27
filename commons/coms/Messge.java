package coms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Arrays;


public class Messge {
	//compos d'un message
	private int id;
	private int authorId;
	private int salleId;
	private String content;
	
	//Nouveau message
	public Messge(int id, int authorId, int salleId,String content) {
		this.id = id;
		this.authorId= authorId;
		this.salleId = salleId;
		this.content = content;
	}
	
	//Reconstruction à partir d'un byte[] reçu
	public Messge(byte[] by) {
		//le byte[] contient 4 section - 3 ids et le message lui-même.
		//on définit les index des limites de chaque block
		int limit1 = Utils.messageIdBytes;
		int limit2 = Utils.authorIdBytes + Utils.messageIdBytes;
		int limit3 = Utils.authorIdBytes + Utils.messageIdBytes + Utils.salleIdBytes;
		
		//Casser le byte[] en plusieurs sous-byte[] et parser en INt, String.
		this.id = Utils.bytesToInt(Arrays.copyOfRange(by, 0, limit1));
		this.authorId= Utils.bytesToInt(Arrays.copyOfRange(by, limit1, limit2));
		this.salleId = Utils.bytesToInt(Arrays.copyOfRange(by, limit2, limit3));
		this.content = new String(Arrays.copyOfRange(by, limit3, by.length));
	}
	
	
	public byte[] toBytes() throws IOException {
		/*Convertir tout le msg en bytes pour envoie*/
		byte byteRep[] = merge4Arrays(Utils.intTo4Bytes(id), Utils.intTo4Bytes(authorId), 
				Utils.intTo4Bytes(salleId), content.getBytes());
		
		return byteRep;
	}
	
	public byte[] merge4Arrays(byte[] a, byte[] b,byte[] c,byte[] d) throws IOException {
		/*Un peu paresseux, pourrait être plus générique/intelligent*/
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		outputStream.write( a );
		outputStream.write( b );
		outputStream.write( c );
		outputStream.write( d );
		byte e[] = outputStream.toByteArray();
		return e;
	}
	
	public String toString() {
		return "ID:" + Integer.toString(id) + " author:"+ Integer.toString(authorId) + " salleId:"+ Integer.toString(salleId) + " " + content;
	}

}
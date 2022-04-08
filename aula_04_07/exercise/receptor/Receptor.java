package aula_04_07.exercise.receptor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receptor {
    public static void main(String args[]) {
        System.out.println("\n\nReceptor\n\n");

    	DatagramSocket aSocket = null;
		try{
	    	aSocket = new DatagramSocket(Integer.parseInt(args[0]));
					// create socket at agreed port
			byte[] buffer = new byte[1000];
 			while(true){
 				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
  				aSocket.receive(request);
                System.out.flush();
				System.out.println("Mensagem recebida: " + new String(request.getData()));
    		}
		}
		catch(SocketException e)
		{
			System.out.println("Socket: " + e.getMessage());
		}
		catch(IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
		finally {
			if(aSocket != null) aSocket.close();
		}
    }
}

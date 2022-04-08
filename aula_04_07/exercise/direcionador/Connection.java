package aula_04_07.exercise.direcionador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;

	public Connection (Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream( clientSocket.getInputStream());
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		}
		catch(IOException e) {
			System.out.println("Connection:"+e.getMessage());
		}
	}

    private void sendToReceptors(String message) {
        DatagramSocket aSocket = null;

        try {
            aSocket = new DatagramSocket();    
            byte [] m = message.getBytes();
            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort1 = 5005;
            int serverPort2 = 5006;
            int serverPort3 = 5007;
            DatagramPacket request1 = new DatagramPacket(m, message.length(), aHost, serverPort1);
            DatagramPacket request2 = new DatagramPacket(m, message.length(), aHost, serverPort2);
            DatagramPacket request3 = new DatagramPacket(m, message.length(), aHost, serverPort3);
            aSocket.send(request1);	
            aSocket.send(request2);	
            aSocket.send(request3);	
        }
        catch(SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        }
        catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
        finally {
            if(aSocket != null) aSocket.close();
        }
    }

	public void run() {
		try {			                 // an echo server
			String data = in.readUTF();	                  // read a line of data from the stream
            this.sendToReceptors(data);
		}
		catch(EOFException e) {
			System.out.println("EOF:"+e.getMessage());
		}
		catch(IOException e) {
			System.out.println("readline:"+e.getMessage());
		}
		finally {
			try {
				clientSocket.close();
			}
			catch(IOException e) {
				/*close failed*/
			}
		}
	}
}

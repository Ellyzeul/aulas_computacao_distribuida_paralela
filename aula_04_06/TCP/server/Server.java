package aula_04_06.TCP.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


class Server {
    public static void main (String args[]) {
		try{
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Conexao recebida de cliente TCP");
				Connection c = new Connection(clientSocket);
			}
		} 
		catch(IOException e) {
			System.out.println("Listen socket:"+e.getMessage());
		}
	}
}
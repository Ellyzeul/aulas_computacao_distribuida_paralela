package aula_04_06.TCP.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import aula_04_06.TCP.server.Connection;

class Server {
    public static void main (String args[]) {
		try{
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Conexao recebida");
				Connection c = new Connection(clientSocket);
			}
		} catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
	}
}
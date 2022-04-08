package aula_04_07.exercise.direcionador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Direcionador {
    public static void main (String args[]) {
		try{
            System.out.println("\n\nDirecionador\n\n");

			int serverPort = 6500; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Conexao recebida do Emissor");
				Connection c = new Connection(clientSocket);
			}
		} 
		catch(IOException e) {
			System.out.println("Listen socket:"+e.getMessage());
		}
	}
}

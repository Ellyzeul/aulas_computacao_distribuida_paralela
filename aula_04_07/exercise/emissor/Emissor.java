package aula_04_07.exercise.emissor;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Emissor {
    public static void main (String args[]) {
		// arguments supply message and hostname
		Socket s = null;
		try{
            System.out.println("\n\nEmissor\n\nMensagem enviada: " + args[0]);

			int serverPort = 6500;
			s = new Socket("localhost", serverPort);
			DataOutputStream out =new DataOutputStream(s.getOutputStream());
			out.writeUTF(args[0]);      	// UTF is a string encoding see Sn. 4.4
		}
		catch (UnknownHostException e) {
			System.out.println("Socket:"+e.getMessage());
		}
		catch (EOFException e) {
			System.out.println("EOF:"+e.getMessage());
		}
		catch (IOException e){
			System.out.println("readline:"+e.getMessage());
		}
		finally {
			if(s!=null) try {
				s.close();
			}
			catch(IOException e) {
				System.out.println("close:"+e.getMessage());
			}
		}
     }
}

# Aula dia 07/04/2022

## Índice
- [Exemplo prático](#section_1)
- [Utilizando threads](#section_2)
- [Implementando Runnable](#section_3)
- [Exercício](#section_4):
    - Comunicação entre emissor com direcionador e direcionador com receptores

## Exemplo prático <a id="section_1"></a>

Foram passadas as seguintes classes:

```java
class Paralelo {
	private int loop;
	private String sFlechas;
	public void setFlechas(int nFlechas) {
		sFlechas ="";
		for (int i=0;i<nFlechas;i++) {
			sFlechas += ">";
		}
	}
	public void setLoop(int loop) {
		this.loop = loop;
	}
	public void roda() {
		for (int i=0;i<loop;i++) {
			System.out.println(sFlechas + " " + i);
		}
	}
}

public class LinhaExecucao { // ainda sem threads
	public static void main(String[] args) {
		Paralelo par1 = new Paralelo();
		Paralelo par2 = new Paralelo();
		par1.setFlechas(5);
		par1.setLoop(20);
		par2.setFlechas(10);
		par2.setLoop(20);
		par1.roda();
		par2.roda();
	}
}
```

Nesse caso a classe ```Paralelo``` não de fato roda em paralelo (ainda) mas representa o que rodará.

O método ```setFlechas``` indica quantos símbolos ">" devem ser impressos ao chamar o método ```roda```, o método ```setLoop``` indica quantos números de 0 a n-1 serão impressos a cada loop.

Por fim o método ```roda``` emula o método ```run``` de uma thread. O resultado da execução da classe principal ```LinhaExecucao``` será:

```
>>>>> 0
>>>>> 1
>>>>> 2
>>>>> 3

. . .

>>>>> 19
>>>>>>>>>> 0
>>>>>>>>>> 1
>>>>>>>>>> 2
>>>>>>>>>> 3

. . .

>>>>>>>>>> 19
```

Como a classe ```Paralelo``` não herda da classe ```Thread``` ou não implementa a interface ```Runnable``` para rodar em uma thread, ela de fato não roda em paralelo e por isso sua execução será sequencial, como qualquer outro código.

### Utilizando threads <a id="section_2"></a>

Como no exemplo anterior não foram utilizadas threads, o código ficou completamente sequencial, então fazendo ```Paralelo``` herdar de ```Thread``` pode-se ver como essa execução em paralelo realmente ocorre.

```java
public class Paralelo extends Thread {
	private int loop;
	private String sFlechas;
	public void setFlechas(int nFlechas) {
		sFlechas ="";
		for (int i=0;i<nFlechas;i++) {
			sFlechas += ">";
		}
	}
	public void setLoop(int loop) {
		this.loop = loop;
	}
	public void run() {
		for (int i=0;i<loop;i++) {
			System.out.println(sFlechas + " " + i);
		}
	}
}

public class LinhaExecucao {
    public static void main(String[] args) {
		Paralelo par1 = new Paralelo();
		Paralelo par2 = new Paralelo();
		par1.setFlechas(5);
		par1.setLoop(20);
		par2.setFlechas(10);
		par2.setLoop(20);
		par1.start();
		par2.start();
	}
}
```

Agora como ```Paralelo``` herda de ```Thread``` é possível de fato rodar os comandos de ```LinhaExecucao``` em paralelo, agora chamando o método ```start``` para iniciar a execução da nova thread.

Por conta disso, o resultado impresso no monitor não é tão certinho como o do exemplo anterior, pois agora a execução não é mais sequencial, nada garante que uma thread não seja executada antes da outra, então toda vez que essa nova versão de ```LinhaExecucao``` é executada o resultado final é diferente, pois dependerá de como o sistema operacional irá escalonar as threads, onde nada garante que sempre será o mesmo escalonamento.

A falta de um semáforo para gerenciar a execução das threads gera essa imprevisibilidade na execução, mas esse não é o tópico, apenas um ponto a se atentar.

### Implementando Runnable <a id="section_3"></a>

Já foram vistas as versões sequencial e paralelas desse código, porém em Java existe mais uma forma de escrever esse código, mantendo-o paralelo.

```java
public class Paralelo implements Runnable {
	private int loop;
	private String sFlechas;
	public void setFlechas(int nFlechas) {
		sFlechas ="";
		for (int i=0;i<nFlechas;i++) {
			sFlechas += ">";
		}
	}
	public void setLoop(int loop) {
		this.loop = loop;
	}
	public void run() {
		for (int i=0;i<loop;i++) {
			System.out.println(sFlechas + " " + i);
		}
	}
}

public class LinhaExecucao {
    public static void main(String[] args) {
		Paralelo par1 = new Paralelo();
		Paralelo par2 = new Paralelo();
		par1.setFlechas(5);
		par1.setLoop(20);
		par2.setFlechas(10);
		par2.setLoop(20);

		Thread th1 = new Thread(par1);
		Thread th2 = new Thread(par2);

		th1.start();
		th2.start();
	}
}
```

Java possui a interface ```Runnable``` que permite que uma classe possa ser executada dentro de uma thread, e dessa forma ao executar essa versão de ```LinhaExecucao``` o resultado será o mesmo que o da versão que herda de ```Thread```, ainda sendo imprevisível o resultado final, por conta da falta de um semáforo.

## Exercício <a id="section_4"></a>

O exercício proposto é para que, utilizando os códigos já vistos, que se realize a comunição entre aplicações emissoras com um direcionador, e esse direcionador trasmitindo as mensagens recebidas para todos os receptores, segue o enunciado:

![Página 1 do enunciado](https://raw.githubusercontent.com/Ellyzeul/aulas_computacao_distribuida_paralela/main/.github/images/aula_04_07_img1.png)
![Página 2 do enunciado](https://raw.githubusercontent.com/Ellyzeul/aulas_computacao_distribuida_paralela/main/.github/images/aula_04_07_img2.png)

### Resolução


#### Emissor
O emissor será um cliente TCP:

```java
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
```

A execução dele via linha de comando recebe um argumento, que é a mensagem a ser enviada ao receptor

#### Direcionador

O direcionador será um servidor TCP, que receberá a mensagem do emissor, e ao mesmo tempo um cliente UDP que enviará ao receptores

<a id="class_direcionador"></a>

```java
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
```

A classe Connection diferentemente da classe usada na [aula de 06/04/2022](https://github.com/Ellyzeul/aulas_computacao_distribuida_paralela/blob/main/aula_04_06/README.md) envia a mensagem de volta para o cliente TCP, mas nesse caso a mensagem deverá ser repassada aos receptores, nesse caso o método ```sendToReceptors``` cumpre essa função

#### Receptor

O receptor é um servidor UDP que receberá as mensagens do direcionador

```java
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
```

Ao executar o receptor via linha de comando, ele receberá um argumento, que é a porta que o socket utilizará (a ideia é instanciar três receptores nas portas 5005, 5006 e 5007, como definido no método ```sendToReceptors``` da classe [```Connection```](#class_direcionador))

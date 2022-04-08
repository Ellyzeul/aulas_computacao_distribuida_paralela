package aula_04_07.example.threadless;


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

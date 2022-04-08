package aula_04_07.example.threading;


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

package aula_04_07.example.runnable;

import java.lang.Runnable;


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

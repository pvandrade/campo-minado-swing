package br.com.cod3r.cm;

import br.com.cod3r.cm.model.Tabuleiro;
import br.com.cod3r.cm.vision.TabuleiroConsole;

public class Aplication {
	
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);
	}
}
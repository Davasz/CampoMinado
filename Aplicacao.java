package br.com.davas.cm;


import br.com.davas.cm.modelo.Tabuleiro;
import br.com.davas.cm.visao.TabuleiroConsole;


public class Aplicacao {
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 3);
		new TabuleiroConsole(tabuleiro);
		
	
	}
}

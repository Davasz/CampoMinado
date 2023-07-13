package br.com.davas.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.davas.cm.excecao.ExplosaoException;
import br.com.davas.cm.excecao.SairException;
import br.com.davas.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	
	private Tabuleiro tabuleiro;
	private Scanner input = new Scanner(System.in);

	

	
	
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while (continuar) {
				cicloJogo();	
				
				System.out.println("Comecar jogo? (S/n)");
				String resposta = input.nextLine();
				
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
				
			}
			
		} catch (SairException e) {
			System.out.println("Jogo Encerrado!");
		} finally {
			input.close();
		}
		
	}

	private void cicloJogo() {
		try {
			
			while (!tabuleiro.objetivoFinalizado()) {
				System.out.println(tabuleiro);
				
				String digitado = valorDigitado("Digite (x, y):");
				
			Iterator<Integer> xy = Arrays
					.stream(digitado.split(","))
					.map(s -> Integer.parseInt(s.trim())).iterator();
				
				digitado = valorDigitado("1- Abrir ou 2- (Des)Marcar:");
				
				if(digitado.equalsIgnoreCase("1")) {
					tabuleiro.open(xy.next(), xy.next());
				} else if(digitado.equalsIgnoreCase("2")) {
					tabuleiro.mark(xy.next(), xy.next());
				}
			}
			
			System.out.println("Voce ganhou!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Voce perdeu!");
		}
		
	}
	
	private String valorDigitado(String texto) {
		System.out.print(texto);
		String digitado = input.nextLine();
		
		if(digitado.equalsIgnoreCase("sair")) {
			throw new SairException();
		}
		
		return digitado;
	}
}

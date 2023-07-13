package br.com.davas.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.davas.cm.excecao.ExplosaoException;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private int bombs;
	
	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int bombs) {
			this.linhas = linhas;
			this.colunas = colunas;
			this.bombs = bombs;
		
			gerarCampos();
			associarVizinhos();
			sortearBombs();

	}
	
		
	public void open(int linha, int coluna) {
		
		
		try {
			campos.parallelStream()
			.filter(c -> c.getPositionX() == linha &&
			c.getPositionY() == coluna)
			.findFirst()
			.ifPresent(c -> c.open());
		} catch (ExplosaoException e) {
			campos.forEach(c-> c.setOpen(true));
			throw e;
		}
	}
	
	public void mark(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getPositionX() == linha &&
		c.getPositionY() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}
	

	private void sortearBombs() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.haveBomb();
		
		do {
			int random = (int) (Math.random() * campos.size());
			campos.get(random).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while(minasArmadas < bombs);
		
	}

	private void associarVizinhos() {
		for(Campo c1:campos) {
			for(Campo c2:campos) {
				c1.addVizinho(c2);
			}
		}
		
	}

	private void gerarCampos() {
		
		for(int l = 0; l < linhas; l++) {
			for(int c = 0; c < colunas; c++) {
				campos.add(new Campo(l, c));
			}
		}
		
	}
	
	public boolean objetivoFinalizado() {
		return campos.stream()
				.allMatch(c -> c.objetivoFinalizado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearBombs();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		int j = 0;
		
		sb.append(" ");
		for(int k = 0; k < colunas ; k++) {
				sb.append(" ");
				sb.append(k);
				sb.append(" ");
		}
		sb.append("\n");
		
		for(int l = 0; l < linhas; l++) {
			sb.append(j);
			
			for(int c = 0; c < colunas; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			
			j++;
			sb.append("\n");
		}
		
		return sb.toString();
	}
}

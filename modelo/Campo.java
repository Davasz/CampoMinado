package br.com.davas.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.davas.cm.excecao.ExplosaoException;

public class Campo {
	
	private final int positionX;
	private final int positionY;
	
	private boolean isMarked = false;
	private boolean haveBomb;
	private boolean isOpen = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna) {
		this.positionX = linha;
		this.positionY= coluna;
	}
	
	boolean addVizinho(Campo vizinho) {
		boolean linhaDiferente = positionX != vizinho.positionX;
		boolean colunaDiferente = positionY != vizinho.positionY;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaX = Math.abs(positionX - vizinho.positionX);
		int deltaY = Math.abs(positionY - vizinho.positionY);
		int deltaGeral = deltaX + deltaY;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}
		if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
		
		
	}
	
	void alternarMarcacao() {
		if(!isOpen) {
			isMarked = !isMarked;
		}
	}
	
	
	boolean open() {
		if(!isOpen && !isMarked) {
			isOpen = true;
			
			if(haveBomb) {
				throw new ExplosaoException();
			}
			
			if(vizinhosSeguros()) {
				vizinhos.forEach(v -> v.open());
			}
			
			return true;
		} else {
			return false;			
		}
	}
	
	boolean vizinhosSeguros() {
		return vizinhos.stream().allMatch(v -> !v.haveBomb);
	}
	
	void minar() {
		haveBomb = true;
	}
	
	
	public boolean isMarked() {
		return isMarked;
	}
	
	public boolean haveBomb() {
		return haveBomb;
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}
	
	boolean objetivoFinalizado() {
		boolean desvendado = !haveBomb && isOpen;
		boolean protegido = haveBomb && isMarked;
		
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.haveBomb).count();
	}
	
	void reiniciar() {
		isMarked = false;
		isOpen = false;
		haveBomb = false;
	}
	
	public String toString() {
		if(isMarked) {
			return "x";
		} else if(isOpen && haveBomb) {
			return "*";
		} else if(isOpen && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		} else if(isOpen) {
			return " ";
		} else {
			return "?";
		}
	}
}

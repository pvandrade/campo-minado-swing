package br.com.cod3r.cm.model;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.exception.ExplosionException;

public class Casa {
	private final int linha;
	private final int coluna;
	
	private boolean aberta;
	private boolean minada;
	private boolean marcada;
	
	private List<Casa> vizinhanca = new ArrayList<>();
	
	Casa(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean addVizinho(Casa vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if (deltaGeral == 1 && !diagonal) {
			vizinhanca.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhanca.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	
	void addMarcacao() {
		if (!aberta) {
			marcada = !marcada;
		}
	}
	
	boolean abrir() {
		if (!aberta && !marcada) {
			aberta = true;
			if (minada) {
				throw new ExplosionException();
			}
			if (vizinhancaSegura()) {
				vizinhanca.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;			
		}
	}	
	
	boolean vizinhancaSegura() {
		return vizinhanca.stream().noneMatch(v -> v.minada);
	}
	
	void minar() {
		minada = true;
	}
	
	public boolean isMinada() {
		return minada;
	}
	
	public boolean isMarcada() {
		return marcada;
	}
	
	void setAberta(boolean aberta) {
		this.aberta = aberta;
	}
	
	public boolean isAberta() {
		return aberta;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendada = !minada && aberta;
		boolean protegida = minada && marcada;
		return desvendada || protegida;
	}
	
	long minasVizinhanca() {
		return vizinhanca.stream().filter(v -> v.minada).count();
	}
	
	void reiniciar() {
		aberta = false;
		minada = false;
		marcada = false;
	}
	
	public String toString() {
		if (marcada) {
			return "x";
		} else if (aberta && minada) {
			return "*";
		} else if (aberta && minasVizinhanca() > 0) {
			return Long.toString(minasVizinhanca());
		} else if (aberta) {
			return " ";
		} else {
			return "?";
		}
	}
}
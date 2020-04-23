package br.com.cod3r.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Casa {
	private final int linha;
	private final int coluna;
	
	private boolean aberta;
	private boolean minada;
	private boolean marcada;
	
	private List<Casa> vizinhanca = new ArrayList<>();
	private List<CasaObservador> observadores = new ArrayList<>();
	
	Casa(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void registrarObservador(CasaObservador observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(CasaEvento evento) {
		observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
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
	
	public void addMarcacao() {
		if (!aberta) {
			marcada = !marcada;
			if(marcada) {
				notificarObservadores(CasaEvento.MARCAR);
			} else {
				notificarObservadores(CasaEvento.DESMARCAR);
			}
		}
	}
	
	public boolean abrir() {
		if (!aberta && !marcada) {
			if (minada) {
				notificarObservadores(CasaEvento.EXPLODIR);
				return true;
			}
			setAberta(true);
			
			if (vizinhancaSegura()) {
				vizinhanca.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;			
		}
	}	
	
	public boolean vizinhancaSegura() {
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
		if (aberta)
			notificarObservadores(CasaEvento.ABRIR);
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
	
	public int minasVizinhanca() {
		return (int) vizinhanca.stream().filter(v -> v.minada).count();
	}
	
	void reiniciar() {
		aberta = false;
		minada = false;
		marcada = false;
		notificarObservadores(CasaEvento.REINICIAR);
	}
}
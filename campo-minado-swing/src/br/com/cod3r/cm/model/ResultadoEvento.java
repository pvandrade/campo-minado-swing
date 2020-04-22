package br.com.cod3r.cm.model;

public class ResultadoEvento {
	private final boolean venceu;
	
	public ResultadoEvento(boolean venceu) {
		this.venceu = venceu;
	}
	
	public boolean isVenceu() {
		return venceu;
	}
}

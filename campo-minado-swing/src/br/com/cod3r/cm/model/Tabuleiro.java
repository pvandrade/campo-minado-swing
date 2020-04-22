package br.com.cod3r.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.cm.exception.ExplosionException;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Casa> casas = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCasas();
		associarVizinhos();
		sortearMinas();
	}

	public void abrir(int linha, int coluna) {
		try {
			casas.parallelStream()
				.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
				.findFirst()
				.ifPresent(c -> c.abrir());
		} catch (ExplosionException e) {
			casas.forEach(c -> c.setAberta(true));
			throw e;
		}
	}
	
	public void marcar(int linha, int coluna) {
		casas.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.addMarcacao());
	}
	
	private void gerarCasas() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				casas.add(new Casa(linha, coluna));
			}
		}
	}
	
	private void associarVizinhos() {
		for(Casa c1: casas) {
			for (Casa c2: casas) {
				c1.addVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Casa> minada = c -> c.isMinada();
		do {
			int aleatorio = (int) (Math.random() * casas.size());
			casas.get(aleatorio).minar();
			minasArmadas = casas.stream().filter(minada).count();
		} while (minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		return casas.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		casas.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for (int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		int i = 0;
		for (int l = 0; l < linhas; l++) {
			sb.append(l);
			sb.append(" ");
			
			for (int c = 0; c < colunas; c++) {
				sb.append(" ");
				sb.append(casas.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

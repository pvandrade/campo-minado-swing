package br.com.cod3r.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CasaObservador {
	
	private final int linhas;
	private final int colunas;
	private final int minas;
	
	private final List<Casa> casas = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCasas();
		associarVizinhos();
		sortearMinas();
	}
	
	public void paraCada(Consumer<Casa> funcao) {
		casas.forEach(funcao);
	}
	
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(boolean resultado) {
		observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}

	public void abrir(int linha, int coluna) {
		casas.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
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
				Casa casa = new Casa(linha, coluna);
				casa.registrarObservador(this);
				casas.add(casa);
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

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	@Override
	public void eventoOcorreu(Casa casa, CasaEvento evento) {
		if (evento == CasaEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		} else if (objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}
	
	private void mostrarMinas() {
		casas.stream()
			.filter(c -> c.isMinada())
			.forEach(c -> c.setAberta(true));	
	}
}

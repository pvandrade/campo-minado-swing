package br.com.cod3r.cm.vision;

import java.awt.GridLayout;

import javax.swing.JPanel;

import br.com.cod3r.cm.model.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {
	public PainelTabuleiro(Tabuleiro tabuleiro) {
		setLayout(new GridLayout(
				tabuleiro.getLinhas(), tabuleiro.getColunas()));
		tabuleiro.paraCada(c -> add(new BotaoCasa(c)));
		
		tabuleiro.registrarObservador(e -> {
			// TODO mostrar resultado para o usuário
			
			tabuleiro.reiniciar();
		});
	}
}

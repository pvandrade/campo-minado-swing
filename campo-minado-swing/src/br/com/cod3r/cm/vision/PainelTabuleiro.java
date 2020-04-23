package br.com.cod3r.cm.vision;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.cod3r.cm.model.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {
	
	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(
				tabuleiro.getLinhas(), tabuleiro.getColunas()));
		
		tabuleiro.paraCada(c -> add(new BotaoCasa(c)));
		tabuleiro.registrarObservador(e -> {
			
			SwingUtilities.invokeLater(() -> {
				if (e.isVenceu()) {
					JOptionPane.showMessageDialog(this, "Parabéns, você venceu!");
				} else {
					JOptionPane.showMessageDialog(this, "Que pena, você perdeu!");
				}
				tabuleiro.reiniciar();
			});
		});
	}
}

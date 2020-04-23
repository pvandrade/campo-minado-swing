package br.com.cod3r.cm.vision;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.cod3r.cm.model.Casa;
import br.com.cod3r.cm.model.CasaEvento;
import br.com.cod3r.cm.model.CasaObservador;

@SuppressWarnings("serial")
public class BotaoCasa extends JButton implements CasaObservador, MouseListener {
	
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCAR = new Color(10, 175, 250);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXT_VERDE = new Color(0, 100, 0);
	
	private Casa casa;
	
	public BotaoCasa(Casa casa) {
		this.casa = casa;
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		setBackground(BG_PADRAO);
		
		addMouseListener(this);
		casa.registrarObservador(this);
	}

	@Override
	public void eventoOcorreu(Casa casa, CasaEvento evento) {
		switch(evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadrao();
		}
	}

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setForeground(Color.LIGHT_GRAY);
		setText("X");
	}

	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCAR);
		setForeground(Color.BLACK);
		setText("M");
	}

	private void aplicarEstiloAbrir() {
		setBackground(BG_PADRAO);
		
		if (casa.isMinada()) {
			setBackground(BG_EXPLODIR);
			return;
		}
	
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		switch (casa.minasVizinhanca()) {
		case 1:
			setForeground(TEXT_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.DARK_GRAY);
		}
		
		String valor = !casa.vizinhancaSegura() ? casa.minasVizinhanca() + "" : "";
		setText(valor);
	}
	
	// interface dos eventos do mouse
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			casa.abrir();
		} else {
			casa.addMarcacao();
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}

package br.com.cod3r.cm.vision;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.cod3r.cm.model.Casa;
import br.com.cod3r.cm.model.CasaEvento;
import br.com.cod3r.cm.model.CasaObservador;

@SuppressWarnings("serial")
public class BotaoCasa extends JButton implements CasaObservador {
	
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCAR = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXT_VERDE = new Color(0, 100, 0);
	
	private Casa casa;
	
	public BotaoCasa(Casa casa) {
		this.casa = casa;
		
		setBorder(BorderFactory.createBevelBorder(0));
		setBackground(BG_PADRAO);
		
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
		// TODO Auto-generated method stub
		
	}

	private void aplicarEstiloExplodir() {
		// TODO Auto-generated method stub
		
	}

	private void aplicarEstiloMarcar() {
		// TODO Auto-generated method stub
		
	}

	private void aplicarEstiloAbrir() {
		// TODO Auto-generated method stub
		
	}
}

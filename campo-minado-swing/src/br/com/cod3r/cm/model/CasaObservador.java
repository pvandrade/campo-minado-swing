package br.com.cod3r.cm.model;

@FunctionalInterface
public interface CasaObservador {
	public void eventoOcorreu(Casa casa, CasaEvento evento);
}

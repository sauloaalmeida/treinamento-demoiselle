package br.gov.serpro.agendafinanceira.config;

import br.gov.frameworkdemoiselle.configuration.Configuration;

@Configuration(resource = "compromisso")
public class CompromissoConfig {
	
	private int valorMinimo;
	
	public int getValorMinimo() {
		return valorMinimo;
	}

}

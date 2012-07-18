package br.gov.serpro.agendafinanceira.business;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.serpro.agendafinanceira.domain.Compromisso;

@BusinessController
public class AgendaFinanceiraBC {
	
	@Inject
	private CompromissoBC compromissoBC;
	
	public List<Compromisso>findAll(){
		return compromissoBC.findAll();		
	}
	
	public void delete(Long id) {
		compromissoBC.delete(id);		
	}

	public void insert(Compromisso bean) {
		compromissoBC.insert(bean);
		
	}

	public void update(Compromisso bean) {
		compromissoBC.update(bean);		
	}

	public Compromisso load(Long id) {
		return compromissoBC.load(id);
	}
	
	public void load() {
		compromissoBC.load();
	}

}

package br.gov.serpro.agendafinanceira.persistence;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.serpro.agendafinanceira.domain.Compromisso;

@PersistenceController
public class CompromissoDAO extends JPACrud<Compromisso, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	public List<Compromisso> findByNome(Compromisso compromisso) {
        return getEntityManager().createQuery("select c from Compromisso c where c.nomeCompromisso = :nomeCompromisso",Compromisso.class)
        						.setParameter("nomeCompromisso", compromisso.getNomeCompromisso())	
        						.getResultList();
     }
	
}

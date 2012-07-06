package br.gov.serpro.agendafinanceira.business;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.annotation.Startup;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.gov.serpro.agendafinanceira.config.CompromissoConfig;
import br.gov.serpro.agendafinanceira.domain.Compromisso;
import br.gov.serpro.agendafinanceira.exception.CompromissoDuplicadoException;
import br.gov.serpro.agendafinanceira.exception.CompromissoValorMinimoException;
import br.gov.serpro.agendafinanceira.persistence.CompromissoDAO;

@BusinessController
public class CompromissoBC extends DelegateCrud<Compromisso, Long, CompromissoDAO> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger logger;
	
	@Inject 
	private CompromissoConfig config;
	
	@Inject 
	private ResourceBundle bundle;
	
	@Inject
	private CompromissoDAO compromissoDao;
	
	@Inject 
	private MessageContext messageContext;
	
	@Startup
	@Transactional
	public void load() {
		
		Date agora = new Date();
		long tempoUmDia = 1000*60*60*24;
		long tempoDoisDias = 1000*60*60*24*2;
		
		 Date ontem = new Date(agora.getTime()-tempoUmDia);
		 Date antesOntem = new Date(agora.getTime()-tempoDoisDias);
		
				
		if (findAll().isEmpty()) {
			insert(new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100")));
			insert(new Compromisso("Gas",ontem,antesOntem,new BigDecimal("566"),new BigDecimal("566")));
			insert(new Compromisso("Agua",ontem,antesOntem,new BigDecimal("230"),new BigDecimal("230")));
			insert(new Compromisso("Cartao Visa",ontem,antesOntem,new BigDecimal("1050"),new BigDecimal("1050")));
			insert(new Compromisso("Cartao Master",ontem,antesOntem,new BigDecimal("2368"),new BigDecimal("2368")));
		}
	}
	
	
	@Override
	public void insert(Compromisso compromisso) {
	
		validaRegrasInsercaoUpdate(compromisso);
		
		//se chegou ate aqui insere o bean
		super.insert(compromisso);
		
		//prepara mensagem de sucesso
		String mensagem = bundle.getString("compromisso.sucesso", compromisso.getNomeCompromisso());
		
		//loga o sucesso
		logger.info(mensagem);
		
		//adiciona mensagem de sucesso
		messageContext.add(mensagem);
	}
	
	@Override
	public void update(Compromisso compromisso) {
		

		validaRegrasInsercaoUpdate(compromisso);
		
		//se chegou ate aqui insere o bean
		super.update(compromisso);
		
	
		//prepara mensagem de sucesso
		String mensagem = bundle.getString("compromisso.sucesso", compromisso.getNomeCompromisso());
				
		//loga o sucesso
		logger.info(mensagem);
		
		//adiciona mensagem de sucesso
		messageContext.add(mensagem);
	}

	@ExceptionHandler
    public void tratar(RuntimeException e){
            logger.error(bundle.getString("compromisso.insert.exception"));
    }

	private void validaRegrasInsercaoUpdate(Compromisso compromisso) {
		
		//se tiver compromisso duplicado
		if(verificaCompromissoDupicado(compromisso)) {
			throw new CompromissoDuplicadoException(bundle.getString("compromisso.duplicado.exception", compromisso.getNomeCompromisso()));
		}
		
		//verifica se o valor esta acima do limite minimo
		if(compromisso.getValorCompromisso().compareTo(new BigDecimal(String.valueOf(config.getValorMinimo()))) < 0) {
			throw new CompromissoValorMinimoException(bundle.getString("compromisso.valor.minimo.exception", compromisso.getNomeCompromisso(), compromisso.getValorCompromisso(),config.getValorMinimo()));
		}

	}
	
	
    private boolean verificaCompromissoDupicado(Compromisso compromisso) {
		return (compromissoDao.findByNome(compromisso).size() > 0);
	}
	
}

package br.gov.serpro.agendafinanceira.domain;

import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;

@RunWith(DemoiselleRunner.class)
public class CompromissoTest {

	private long tempoUmDia = 1000*60*60*24;
	private Date agora = new Date();
	private Date amanha = new Date(agora.getTime()+tempoUmDia);
	private Date ontem = new Date(agora.getTime()-tempoUmDia);
	
	@Test
	public void testaVencimentoNaoAtingido() {
		
		Compromisso compromisso = new Compromisso(1l,"Luz",amanha,null,null,null);
		
		assertFalse(compromisso.isAtrasado());
		
	}
	
	@Test
	public void testaDiaVencimento() {
		
		Compromisso compromisso = new Compromisso(1l,"Luz",agora,null,null,null);
		
		assertFalse(compromisso.isAtrasado());
		
	}	
	
	@Test
	public void testaVencimentoAtingidoCompromissoPagoEmDia() {
		
	}
	
	@Test
	public void testaVencimentoAtingidoCompromissoPagoAtrasadoDia() {
		
	}
	
	@Test
	public void testaVencimentoAtingidoCompromissoNaoPago() {
		
	}	
	
	
}

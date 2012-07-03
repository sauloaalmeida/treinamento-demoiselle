package br.gov.serpro.agendafinanceira.domain;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class CompromissoTest {

	private long tempoUmDia = 1000*60*60*24;
	private long tempoDoisDias = 1000*60*60*24*2;
	private Date agora = new Date();
	private Date amanha = new Date(agora.getTime()+tempoUmDia);
	private Date ontem = new Date(agora.getTime()-tempoUmDia);
	private Date antesOntem = new Date(agora.getTime()-tempoDoisDias);
	
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
		
		Compromisso compromisso = new Compromisso(1l,"Luz",ontem,antesOntem,null,null);
		
		assertFalse(compromisso.isAtrasado());
	}
	
	@Test
	public void testaVencimentoAtingidoCompromissoPagoAtrasadoDia() {
		
		Compromisso compromisso = new Compromisso(1l,"Luz",antesOntem,ontem,null,null);
		
		assertTrue(compromisso.isAtrasado());
	}
	
	@Test
	public void testaVencimentoAtingidoCompromissoNaoPagoNull() {
		
		Compromisso compromisso = new Compromisso(1l,"Luz",antesOntem,null,null,null);
		
		assertTrue(compromisso.isAtrasado());
		
	}
	
	@Test
	public void testaVencimentoAtingidoCompromissoNaoPagoDataFutura() {
		
		Compromisso compromisso = new Compromisso(1l,"Luz",antesOntem,amanha,null,null);
		
		assertTrue(compromisso.isAtrasado());
		
	}		
	
	
}

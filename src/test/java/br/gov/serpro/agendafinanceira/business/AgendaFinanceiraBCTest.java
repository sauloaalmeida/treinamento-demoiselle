package br.gov.serpro.agendafinanceira.business;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.serpro.agendafinanceira.config.CompromissoConfig;
import br.gov.serpro.agendafinanceira.domain.Compromisso;
import br.gov.serpro.agendafinanceira.exception.CompromissoDuplicadoException;
import br.gov.serpro.agendafinanceira.exception.CompromissoValorMinimoException;

@RunWith(DemoiselleRunner.class)
public class AgendaFinanceiraBCTest {

	@Inject
	private EntityManager em;
	
	@Inject
	private AgendaFinanceiraBC agendaFinanceiraBC;
	
	@Inject 
	private CompromissoConfig config;
	
	private Date agora = new Date();
	private long tempoUmDia = 1000*60*60*24;
	private long tempoDoisDias = 1000*60*60*24*2;	
	private Date ontem = new Date(agora.getTime()-tempoUmDia);
	private Date antesOntem = new Date(agora.getTime()-tempoDoisDias);
	
	@Before
	@Transactional
	public void before() {
		
		List<Compromisso> listaCompromisso = em.createQuery("SELECT c FROM Compromisso c",Compromisso.class).getResultList();
	
		for (Compromisso compromisso : listaCompromisso) {
			em.remove(compromisso);
		}
	}
	
	@Test
	public void testLoad() {
		agendaFinanceiraBC.load();
		List<Compromisso> listaCompromissos = agendaFinanceiraBC.findAll();
		assertNotNull(listaCompromissos);
		assertEquals(5, listaCompromissos.size());
	}
	
	@Test
	public void testInsertSucesso() {
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100"));
		agendaFinanceiraBC.insert(compromisso);
		List<Compromisso> listaCompromissos = agendaFinanceiraBC.findAll();
		assertNotNull(listaCompromissos);
		assertEquals(1, listaCompromissos.size());
	}
	
	@Test(expected=CompromissoValorMinimoException.class)
	public void testInsertErroValorMinimo() {
		
		BigDecimal valorMinimo = new BigDecimal(String.valueOf(config.getValorMinimo()));
		BigDecimal menorValorMinimo = valorMinimo.subtract(new BigDecimal("1"));
		
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,menorValorMinimo,menorValorMinimo);
		agendaFinanceiraBC.insert(compromisso);
	}
	
	@Test(expected=CompromissoDuplicadoException.class)
	public void testInsertErroCompromissoDuplicado() {
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100"));
		Compromisso compromisso2 = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("190"),new BigDecimal("190"));
		
		agendaFinanceiraBC.insert(compromisso);
		agendaFinanceiraBC.insert(compromisso2);
	}		
	
	@Test
	public void testDelete() {
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100"));
		agendaFinanceiraBC.insert(compromisso);
		
		List<Compromisso> listaCompromissos = agendaFinanceiraBC.findAll();
		assertNotNull(listaCompromissos);
		assertEquals(1, listaCompromissos.size());
		
		agendaFinanceiraBC.delete(compromisso.getId());
		listaCompromissos = agendaFinanceiraBC.findAll();
		assertEquals(0, listaCompromissos.size());
	}
	
	@Test
	public void testUpdateSucesso() {
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100"));
		agendaFinanceiraBC.insert(compromisso);
		
		List<Compromisso> listaCompromissos = agendaFinanceiraBC.findAll();
		Compromisso compromisso2 = (Compromisso)listaCompromissos.get(0);
		assertNotNull(listaCompromissos);
		assertEquals("Luz", compromisso2.getNomeCompromisso());
		
		compromisso2.setNomeCompromisso("Gas");
		agendaFinanceiraBC.update(compromisso2);
		
		listaCompromissos = agendaFinanceiraBC.findAll();
		Compromisso compromisso3 = (Compromisso)listaCompromissos.get(0);
		assertEquals("Gas", compromisso3.getNomeCompromisso());
	}
	
	@Test(expected=CompromissoValorMinimoException.class)
	public void testUpdateErroValorMinimo() {
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100"));
		agendaFinanceiraBC.insert(compromisso);
		
		List<Compromisso> listaCompromissos = agendaFinanceiraBC.findAll();
		Compromisso compromisso2 = (Compromisso)listaCompromissos.get(0);
		assertNotNull(listaCompromissos);
		assertEquals("Luz", compromisso2.getNomeCompromisso());
		
		BigDecimal valorMinimo = new BigDecimal(String.valueOf(config.getValorMinimo()));
		BigDecimal menorValorMinimo = valorMinimo.subtract(new BigDecimal("1"));
		
		compromisso2.setValorCompromisso(menorValorMinimo);
		agendaFinanceiraBC.update(compromisso2);
	}
		


}

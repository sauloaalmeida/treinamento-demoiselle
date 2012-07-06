package br.gov.serpro.agendafinanceira.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class CompromissoBCTest {
	
	@Inject
	private EntityManager em;
	
	@Inject
	private CompromissoBC compromissoBC;
	
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
		compromissoBC.load();
		List<Compromisso> listaCompromissos = compromissoBC.findAll();
		assertNotNull(listaCompromissos);
		assertEquals(5, listaCompromissos.size());
	}
	
	@Test
	public void testInsertSucesso() {
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100"));
		compromissoBC.insert(compromisso);
		List<Compromisso> listaCompromissos = compromissoBC.findAll();
		assertNotNull(listaCompromissos);
		assertEquals(1, listaCompromissos.size());
	}
	
	@Test(expected=CompromissoValorMinimoException.class)
	public void testInsertErroValorMinimo() {
		
		BigDecimal valorMinimo = new BigDecimal(String.valueOf(config.getValorMinimo()));
		BigDecimal menorValorMinimo = valorMinimo.subtract(new BigDecimal("1"));
		
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,menorValorMinimo,menorValorMinimo);
		compromissoBC.insert(compromisso);
	}
	
	@Test(expected=CompromissoDuplicadoException.class)
	public void testInsertErroCompromissoDuplicado() {
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100"));
		Compromisso compromisso2 = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("190"),new BigDecimal("190"));
		
		compromissoBC.insert(compromisso);
		compromissoBC.insert(compromisso2);
	}		
	
	@Test
	public void testDelete() {
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100"));
		compromissoBC.insert(compromisso);
		
		List<Compromisso> listaCompromissos = compromissoBC.findAll();
		assertNotNull(listaCompromissos);
		assertEquals(1, listaCompromissos.size());
		
		compromissoBC.delete(compromisso.getId());
		listaCompromissos = compromissoBC.findAll();
		assertEquals(0, listaCompromissos.size());
	}
	@Test
	public void testUpdateSucesso() {
		Compromisso compromisso = new Compromisso("Luz",ontem,antesOntem,new BigDecimal("100"),new BigDecimal("100"));
		compromissoBC.insert(compromisso);
		
		List<Compromisso> listaCompromissos = compromissoBC.findAll();
		Compromisso compromisso2 = (Compromisso)listaCompromissos.get(0);
		assertNotNull(listaCompromissos);
		assertEquals("Luz", compromisso2.getNomeCompromisso());
		
		compromisso2.setNomeCompromisso("Gas");
		compromissoBC.update(compromisso2);
		
		listaCompromissos = compromissoBC.findAll();
		Compromisso compromisso3 = (Compromisso)listaCompromissos.get(0);
		assertEquals("Gas", compromisso3.getNomeCompromisso());
	}

}

package br.gov.serpro.agendafinanceira.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Compromisso  implements Serializable {

	private static final long serialVersionUID = 2190479373816067136L;
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String nomeCompromisso;
	
	@Column
	@Temporal(value=TemporalType.DATE)
	private Date dataVencimento;
	
	@Column
	@Temporal(value=TemporalType.DATE)
	private Date dataPagamento;
	
	@Column
	private BigDecimal valorCompromisso;
	
	@Column	
	private BigDecimal valorPago;
	
	public Compromisso() {
	}

	public Compromisso(Long id, String nomeCompromisso, Date dataVencimento, Date dataPagamento,
			BigDecimal valorCompromisso, BigDecimal valorPago) {
		this.id = id;
		this.nomeCompromisso = nomeCompromisso;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valorCompromisso = valorCompromisso;
		this.valorPago = valorPago;
	}
	
	public boolean isAtrasado() {
		
		Date agora = new Date();
		
		if(agora.getTime() <= dataVencimento.getTime()) {
			return false;	
		}else {
		
			return true;
		}		
		
		
	}

	
	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	
	public String getNomeCompromisso() {
		return nomeCompromisso;
	}

	
	public void setNomeCompromisso(String nomeCompromisso) {
		this.nomeCompromisso = nomeCompromisso;
	}

	
	public Date getDataVencimento() {
		return dataVencimento;
	}

	
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	
	public Date getDataPagamento() {
		return dataPagamento;
	}

	
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	
	public BigDecimal getValorCompromisso() {
		return valorCompromisso;
	}

	
	public void setValorCompromisso(BigDecimal valorCompromisso) {
		this.valorCompromisso = valorCompromisso;
	}

	
	public BigDecimal getValorPago() {
		return valorPago;
	}

	
	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
	
	
	
	

}

package br.gov.serpro.agendafinanceira.view;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.serpro.agendafinanceira.business.AgendaFinanceiraBC;
import br.gov.serpro.agendafinanceira.domain.Compromisso;

@ViewController
@PreviousView("/agendafinanceira_list.xhtml")
public class AgendaFinanceiraEditMB extends AbstractEditPageBean<Compromisso, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgendaFinanceiraBC agendaFinanceiraBC;

	@Override
	@Transactional
	public String delete() {
		this.agendaFinanceiraBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.agendaFinanceiraBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		this.agendaFinanceiraBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.agendaFinanceiraBC.load(getId()));
	}

}

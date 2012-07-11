package br.gov.serpro.agendafinanceira.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.serpro.agendafinanceira.business.AgendaFinanceiraBC;
import br.gov.serpro.agendafinanceira.domain.Compromisso;

@ViewController
@NextView("/agendafinanceira_edit.xhtml")
@PreviousView("/agendafinanceira_list.xhtml")
public class AgendaFinanceiraListMB extends AbstractListPageBean<Compromisso, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgendaFinanceiraBC bc;

	@Override
	protected List<Compromisso> handleResultList() {
		return this.bc.findAll();
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				bc.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}

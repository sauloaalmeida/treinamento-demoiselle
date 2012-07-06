package br.gov.serpro.agendafinanceira.exception;

import br.gov.frameworkdemoiselle.exception.ApplicationException;

@ApplicationException
public class CompromissoDuplicadoException extends RuntimeException {

	private static final long serialVersionUID = 2479188530659537155L;
	
	public CompromissoDuplicadoException(String errorMsg) {
		super(errorMsg);
	}
	
	public CompromissoDuplicadoException(String errorMsg, Throwable cause) {
		super(errorMsg,cause);
	}	

}

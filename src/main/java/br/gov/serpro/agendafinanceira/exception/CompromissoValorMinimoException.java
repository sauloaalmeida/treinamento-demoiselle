package br.gov.serpro.agendafinanceira.exception;

import br.gov.frameworkdemoiselle.exception.ApplicationException;

@ApplicationException
public class CompromissoValorMinimoException extends RuntimeException {

	private static final long serialVersionUID = 20275658158765621L;


	public CompromissoValorMinimoException(String message) {
		super(message);
	}

	public CompromissoValorMinimoException(String message, Throwable cause) {
		super(message, cause);
	}

}

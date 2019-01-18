package br.com.academia.application.util;

public class Validation {

	// verifica se o usu�rio preencheu alguma coisa
	public static void assertNotEmpty(Object obj) {
		if(obj instanceof String) {
			String s = (String) obj;
			
			if(StringUtils.isEmpty(s)) {
				throw new ValidationException("O campo n�o pode ser nulo.");
			}
		}
		
		if(obj == null) {
			throw new ValidationException("O valor n�o pode ser nulo.");
		}
	}
}

package br.com.academia.application.util;

/*
 * Essa classe ter� alguns m�todos utilit�rios
 */

public class StringUtils {

	// verifica se uma String est� vazia
	public static boolean isEmpty(String s) {
		if(s == null) {
			return true;
		}
		
		return s.trim().length() == 0;
	}
	
	// preenche com zeros � esquerda
	public static String leftZero(int value, int finalSize) {
		return String.format("%0" + finalSize + "d", value);
	}
	
	/*
	   // testa o m�todo isEmpty
	   public static void main(String[] args) {
		String str1 = "abc";                  // resultado = false
		String str2 = "   ";                  // resultado = true
		String str3 = null;                   // resultado = true
		String str4 = "  a  ";                // resultado = false
		boolean b = StringUtils.isEmpty(str1);
		System.out.println(b);
	

	    // testa o m�todo leftZero
		int v = 100;
		System.out.println(StringUtils.leftZero(v, 8)); // resultado = 00000100
		
	   }
	
	 */
}

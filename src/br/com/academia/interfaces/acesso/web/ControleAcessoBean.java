package br.com.academia.interfaces.acesso.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named // poderia ter nome: @Named("nome") Quando não tem nome, a JPA usa o nome do bean: controleAcessoBean
@RequestScoped
public class ControleAcessoBean implements Serializable {

	private String matricula;
	private Integer rg;
	
	public String registrarAcesso() {
		return null;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Integer getRg() {
		return rg;
	}
	public void setRg(Integer rg) {
		this.rg = rg;
	}
}

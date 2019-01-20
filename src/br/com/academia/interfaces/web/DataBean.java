package br.com.academia.interfaces.web;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import br.com.academia.domain.aluno.Aluno.Sexo;
import br.com.academia.domain.aluno.Aluno.Situacao;
import br.com.academia.domain.aluno.Estado;

@Named
@ApplicationScoped
public class DataBean implements Serializable {

	public Sexo[] getSexos() {
		return Sexo.values();
	}
	
	public Situacao[] getSituacoes() {
		return Situacao.values();
	}
	
	public List<Estado> getEstados() {
		return null; // será terminado depois
	}
}

package br.com.academia.interfaces.aluno.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.academia.domain.aluno.Aluno;

@Named // poderia dar um nome: @Named(name="nome"); se n�o tem nome, o jsf d� o nome da classe #{alunoBean} com a primeira letra min�scula
@RequestScoped
public class AlunoBean implements Serializable {

	private Aluno aluno = new Aluno();

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	// m�todo chamado pelo bot�o cadastrar da p�gina novoAluno
	public String gravar() {
		System.out.println("Aluno: " + aluno);
		return null; // retorna para a pr�pria tela
	}
}

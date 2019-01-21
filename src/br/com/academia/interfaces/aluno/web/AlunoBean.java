package br.com.academia.interfaces.aluno.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.academia.application.service.AlunoService;
import br.com.academia.domain.aluno.Aluno;

@Named 
@RequestScoped
public class AlunoBean implements Serializable {

	@EJB
	private AlunoService alunoService;
	
	private Aluno aluno = new Aluno();

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	// método chamado pelo botão cadastrar da página novoAluno
	public String gravar() {
		//System.out.println("Aluno: " + aluno);
		alunoService.createOrUpdate(aluno);
		return null;
	}
}

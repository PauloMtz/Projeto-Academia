package br.com.academia.interfaces.aluno.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.academia.application.service.AlunoService;
import br.com.academia.application.util.StringUtils;
import br.com.academia.domain.aluno.Aluno;

@Named 
@RequestScoped
public class AlunoBean implements Serializable {

	@EJB
	private AlunoService alunoService;
	
	@Inject
	private FacesContext facesContext;
	
	private Aluno aluno = new Aluno();
	private String titulo = "Novo Aluno";

	private String matricula;
	
	public void carregar() {
		if(!StringUtils.isEmpty(matricula)) {
			aluno = alunoService.findByMatricula(matricula);
			titulo = "Atualizar Aluno";
		}
	}

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
		facesContext.addMessage(null, new FacesMessage("Dados gravados com sucesso."));
		return null;
	}
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getTitulo() {
		return titulo;
	}

	/*public void setTitulo(String titulo) {
		this.titulo = titulo;
	}*/
}

package br.com.academia.interfaces.relatorio.web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.academia.application.service.AlunoService;
import br.com.academia.domain.aluno.Aluno;
import br.com.academia.domain.aluno.Aluno.Situacao;

@Named
@SessionScoped
public class RelatorioSituacoesBean implements Serializable {

	private Situacao situacao;
	
	private List<Aluno> alunos;
	
	/**
	 * essa classe bean interage com AlunoService, método listSituacoesAluno
	 * a classe AlunoService interage com a classe AlunoRepository, método listSituacoesAluno (mesmo nome)
	 */
	@EJB
	private AlunoService alunoService;
	
	public String gerarRelatorio() {
		alunos = alunoService.listSituacoesAluno(situacao);
		return null;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
}

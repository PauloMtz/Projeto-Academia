package br.com.academia.application.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.ValidationException;

import br.com.academia.application.util.StringUtils;
import br.com.academia.domain.acesso.Acesso;
import br.com.academia.domain.acesso.AcessoRepository;
import br.com.academia.domain.acesso.TipoAcesso;
import br.com.academia.domain.aluno.Aluno;
import br.com.academia.domain.aluno.AlunoRepository;

@Stateless
public class AcessoService {

	@EJB
	private AcessoRepository acessoRepository;
	
	@EJB
	private AlunoRepository alunoRepository;
	
	public TipoAcesso registrarAcesso(String matricula, Integer rg) {
		if(StringUtils.isEmpty(matricula) && rg == null) {
			throw new ValidationException("Informe a matr�cula ou o RG para o acesso.");
		}
		
		Aluno aluno;
		if(StringUtils.isEmpty(matricula)) {
			aluno = alunoRepository.findByRg(rg);
		} else {
			aluno = alunoRepository.findByMatricula(matricula);
		}
		
		if(aluno == null) {
			throw new ValidationException("Aluno n�o encontrado.");
		}
		
		Acesso ultimoAcesso = acessoRepository.findUltimoAcesso(aluno);
		TipoAcesso tipoAcesso;
		
		if(ultimoAcesso == null || ultimoAcesso.isEntradaSaidaPreenchidas()) {
			ultimoAcesso = new Acesso();
			ultimoAcesso.setAluno(aluno);
			tipoAcesso = ultimoAcesso.registrarAcesso();
			acessoRepository.store(ultimoAcesso);
		} else {
			tipoAcesso = ultimoAcesso.registrarAcesso();
		}
		
		return tipoAcesso;
	}
}

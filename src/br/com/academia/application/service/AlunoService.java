package br.com.academia.application.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.academia.application.util.StringUtils;
import br.com.academia.application.util.Validation;
import br.com.academia.application.util.ValidationException;
import br.com.academia.domain.aluno.Aluno;
import br.com.academia.domain.aluno.AlunoRepository;

@Stateless
public class AlunoService {

	@EJB
	private AlunoRepository alunoRepository;
	
	public void createOrUpdate(Aluno aluno) {
		if(StringUtils.isEmpty(aluno.getMatricula())) {
			create(aluno);
		} else {
			update(aluno);
		}
	}
	
	private void create(Aluno aluno) {
		Validation.assertNotEmpty(aluno);
		String maxMatricula = alunoRepository.getMaxMatriculaAno();
		aluno.gerarMatricula(maxMatricula);
		alunoRepository.store(aluno);
	}
	
	private void update(Aluno aluno) {
		Validation.assertNotEmpty(aluno);
		Validation.assertNotEmpty(aluno.getMatricula());
		
		alunoRepository.update(aluno);
	}
	
	public void delete(String matricula) {
		alunoRepository.delete(matricula);
	}
	
	public Aluno findByMatricula(String matricula) {
		return alunoRepository.findByMatricula(matricula);
	}
	
	public List<Aluno> listAlunos(String matricula, String nome, Integer rg, Integer telefone) {
		
		//return List.of(alunoRepository.findByMatricula(matricula));
		
		/*Aluno aluno = alunoRepository.findByMatricula(matricula);
		List<Aluno> alunos = new ArrayList<Aluno>();
		alunos.add(aluno);*/
		
		if(StringUtils.isEmpty(matricula) && StringUtils.isEmpty(nome) && rg == null && telefone == null) {
			throw new ValidationException("Preencha pelo menos um campo para a consulta.");
		}
		return alunoRepository.listAlunos(matricula, nome, rg, telefone);
	}
}

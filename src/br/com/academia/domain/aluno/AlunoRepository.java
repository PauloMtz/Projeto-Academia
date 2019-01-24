package br.com.academia.domain.aluno;

import java.time.Year;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.academia.application.util.StringUtils;

@Stateless
public class AlunoRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void store(Aluno aluno) {
		em.persist(aluno); // basta esta linha para gravar um aluno no banco de dados
	}
	
	public void update(Aluno aluno) {
		em.merge(aluno);
	}
	
	public Aluno findByMatricula(String matricula) {
		return em.find(Aluno.class, matricula);
	}
	
	public void delete(String matricula) {
		Aluno aluno = findByMatricula(matricula);
		
		if(aluno != null) {
			em.remove(aluno);
		}
	}
	
	public String getMaxMatriculaAno() { // atentar para a classe Aluno, n�o � a tabela do banco de dados
		return em.createQuery("SELECT MAX(a.matricula) FROM Aluno a WHERE a.matricula LIKE :ano", String.class)
			.setParameter("ano", Year.now() + "%")
			.getSingleResult();
	}
}

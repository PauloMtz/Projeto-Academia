package br.com.academia.domain.aluno;

import java.time.Year;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ValidationException;

import br.com.academia.application.util.StringUtils;
import br.com.academia.domain.aluno.Aluno.Situacao;

@Stateless
public class AlunoRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private FacesContext facesContext;
	
	public void store(Aluno aluno) {
		em.persist(aluno);
	}
	
	public void update(Aluno aluno) {
		em.merge(aluno);
	}
	
	public Aluno findByMatricula(String matricula) {
		return em.find(Aluno.class, matricula);
	}
	
	public Aluno findByRg(Integer rg) {
		
		try {
			return em.createQuery("SELECT a FROM Aluno a WHERE a.rg = :rg", Aluno.class)
					.setParameter("rg", rg)
					.getSingleResult();
		} catch(ValidationException e) {
			facesContext.addMessage(null, new FacesMessage(e.getMessage()));
		}
		
		return null;
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
	
	public List<Aluno> listAlunos(String matricula, String nome, Integer rg, Integer telefone) {
		StringBuilder jpql = new StringBuilder("SELECT a FROM Aluno a WHERE ");
		
		if(!StringUtils.isEmpty(matricula)) {
			jpql.append("a.matricula = :matricula AND ");
		}
		
		if(!StringUtils.isEmpty(nome)) {
			jpql.append("a.nome LIKE :nome AND ");
		}
		
		if(rg != null) {
			jpql.append("a.rg = :rg AND ");
		}
		
		if(telefone != null) {
			jpql.append("a.telefone.numero = :telefone AND ");
		}
		
		jpql.append("1 = 1"); // � uma condi��o sempre verdadeira, confirmando as condi�oes verdadeiras anteriores
		TypedQuery<Aluno> q = em.createQuery(jpql.toString(), Aluno.class);
		
		if(!StringUtils.isEmpty(matricula)) {
			q.setParameter("matricula", matricula);
		}
		
		if(!StringUtils.isEmpty(nome)) {
			q.setParameter("nome", "%" + nome + "%");
		}
		
		if(rg != null) {
			q.setParameter("rg", rg);
		}
		
		if(telefone != null) {
			q.setParameter("telefone", telefone);
		}
		
		return q.getResultList();
	}
	
	public List<Aluno> listSituacoesAluno(Situacao situacao) {
		return em.createQuery("SELECT a FROM Aluno a WHERE a.situacao = :situacao ORDER BY a.nome", Aluno.class)
				.setParameter("situacao", situacao)
				.getResultList();
	}
}

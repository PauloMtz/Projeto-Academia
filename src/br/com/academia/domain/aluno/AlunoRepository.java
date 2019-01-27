package br.com.academia.domain.aluno;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import br.com.academia.domain.acesso.Acesso;
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
	
	public String getMaxMatriculaAno() { // atentar para a classe Aluno, não é a tabela do banco de dados
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
		
		jpql.append("1 = 1");
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
	
	public List<Acesso> listAcessosAlunos(String matricula, LocalDate dataInicial, LocalDate dataFinal) {
		StringBuilder jpql = new StringBuilder("SELECT a FROM Acesso a WHERE ");
		
		if(!StringUtils.isEmpty(matricula)) {
			jpql.append("a.aluno.matricula = :matricula AND "); // aqui está navegando no relacionamento acesso e aluno
		}
		
		if(dataInicial != null) {
			jpql.append("a.entrada >= :entradaInicio AND a.entrada <= :entradaFim AND "); // aqui está navegando no acesso
			// como LocalDate trabalha com data e hora, tem que especificar a hora inicial e hora final
		}
		
		if(dataFinal != null) {
			jpql.append("a.saida >= :saidaInicio AND a.saida <= :saidaFim AND ");
		}
		
		jpql.append("1 = 1 ORDER BY a.entrada"); // '1 = 1' fecha as condições da pesquisa, e é sempre verdadeiro
		TypedQuery<Acesso> q = em.createQuery(jpql.toString(), Acesso.class);
		
		// agora define os parâmetros
		if(!StringUtils.isEmpty(matricula)) {
			q.setParameter("matricula", matricula);
		}
		
		if(dataInicial != null) {
			LocalDateTime ldt = LocalDateTime.of(dataInicial, LocalTime.of(0, 0, 0));
			q.setParameter("entradaInicio", ldt);
			
			ldt = LocalDateTime.of(dataInicial, LocalTime.of(23, 59, 59));
			q.setParameter("entradaFim", ldt);
		}
		
		if(dataFinal != null) {
			LocalDateTime ldt = LocalDateTime.of(dataFinal, LocalTime.of(0, 0, 0));
			q.setParameter("saidaInicio", ldt);
			
			ldt = LocalDateTime.of(dataFinal, LocalTime.of(23, 59, 59));
			q.setParameter("saidaFim", ldt);
		}
		
		return q.getResultList();
	}
}

package br.com.academia.domain.acesso;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.academia.domain.aluno.Aluno;

@Stateless
public class AcessoRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private FacesContext facesContext;
	
	public Acesso findUltimoAcesso(Aluno aluno) {
		
		// o acesso já tem uma associação com aluno; e ordena pelo id do acesso
		try {
			return em.createQuery("SELECT a FROM Acesso a WHERE a.aluno.matricula = :matricula ORDER BY a.id DESC", Acesso.class) 
					.setParameter("matricula", aluno.getMatricula())
					.setMaxResults(1)
					.getSingleResult();
		} catch(NoResultException  e) {
			return null;
		}
		
	}
	
	public void store(Acesso acesso) {
		em.persist(acesso);
	}
}

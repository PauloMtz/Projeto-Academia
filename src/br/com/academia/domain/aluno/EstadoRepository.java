package br.com.academia.domain.aluno;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class EstadoRepository {
	
	@PersistenceContext
	private EntityManager em; // a partir do EntityManager, pode-se efetuar operações com a JPA no banco de dados

	public List<Estado> listEstados() {
		// atentar para o select na classe Estado, e não na tabela estados do banco de dados
		TypedQuery<Estado> q = em.createQuery("SELECT e FROM Estado e ORDER BY e.nome", Estado.class);
		return q.getResultList();
		
		// poderia ser numa linha só
		// return em.createQuery("SELECT e.sigla, e.nome FROM estados e ORDER BY e.nome", Estado.class).getResultList();
	}
}

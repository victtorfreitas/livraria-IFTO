package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;


public class DAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<T> classe;
	@Inject
	private EntityManager em;

	public DAO(EntityManager em, Class<T> classe) {
		this.em = em;
		this.classe = classe;
	}
	@Transactional
	public void adiciona(T t) {
		// persiste o objeto
		em.persist(t);

	}
	@Transactional
	public void remove(T t) {
		em.remove(em.merge(t));
	}

	public void atualiza(T t) {
		em.merge(t);
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));
		List<T> lista = em.createQuery(query).getResultList();
		return lista;
	}

	public T buscaPorId(Integer id) {
		T instancia = em.find(classe, id);
		return instancia;
	}

	public int contaTodos() {
		long result = (Long) em.createQuery("select count(n) from livro n").getSingleResult();

		return (int) result;
	}

	public int quantidadeDeElementos() {
		long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n").getSingleResult();
		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults, String coluna, String resultadoFiltro) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		Root<T> root = query.from(classe);

		if (resultadoFiltro != null) {
			query = query.where(em.getCriteriaBuilder().like(root.<String>get(coluna), resultadoFiltro + "%"));
		}

		List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		return lista;
	}

}

package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DAO<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Class<T> clazz;
	private EntityManager manager;

	public DAO(EntityManager manager, Class<T> clazz) {
		this.manager = manager;
		this.clazz = clazz;
	}

	public void adiciona(T t) {
		manager.persist(t);
	}

	public void remove(T t) {
		manager.remove(manager.merge(t));
	}

	public void atualiza(T t) {
		manager.merge(t);
	}

	public List<T> listaTodos() {
		List<T> lista = manager.createQuery("FROM " + clazz.getName(), clazz)
				.getResultList();
//		CriteriaQuery<T> query = manager.getCriteriaBuilder().createQuery(classe);
//		query.select(query.from(classe));

//		List<T> lista = manager.createQuery(query).getResultList();

		return lista;
	}

	public T buscaPorId(Integer id) {
		T instancia = manager.find(clazz, id);
		
		return instancia;
	}

	public int contaTodos() {
		long result = (Long) manager.createQuery("select count(n) from Livro n")
				.getSingleResult();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults, String coluna, String valor) {
		CriteriaQuery<T> query = manager.getCriteriaBuilder().createQuery(clazz);
		Root<T> root = query.from(clazz);
		
		if (!Objects.isNull(valor)) {
			query = query.where(manager.getCriteriaBuilder().like(root.<String>get(coluna), valor + "%"));
		}

		List<T> lista = manager.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		return lista;
	}
}
package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public boolean existsByEmailAndPass(String email, String senha) {
		TypedQuery<Usuario> typedQuery = this.manager.createQuery(
				"FROM Usuario u where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
		typedQuery.setParameter("pEmail", email);
		typedQuery.setParameter("pSenha", senha);
		
		try {
			typedQuery.getSingleResult();
		} catch (NoResultException nre) {
			return false;
		}
		
		return true;
	}
}
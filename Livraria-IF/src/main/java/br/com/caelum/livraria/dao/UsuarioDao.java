package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

@SuppressWarnings("serial")
@Stateless
public class UsuarioDao implements Serializable {

	@PersistenceContext 
	private EntityManager em;

	public boolean existe(Usuario usuario) {
		
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where email = :pEmail and senha = :pSenha",
				Usuario.class);
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());

		try {
			@SuppressWarnings("unused")
			Usuario resultado = query.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}
	

}

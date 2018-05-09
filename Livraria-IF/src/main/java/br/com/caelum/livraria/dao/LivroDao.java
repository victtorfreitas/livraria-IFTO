package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.livraria.modelo.Livro;
@SuppressWarnings("serial")
@Stateless
public class LivroDao implements Serializable {
	
	@PersistenceContext
    private EntityManager em;

    private DAO<Livro> dao;

    @PostConstruct
    void init() {
        this.dao = new DAO<Livro>(this.em, Livro.class);
    }

    public Livro buscaPorId(Integer livroId) {
        return this.dao.buscaPorId(livroId);
    }

    public void adiciona(Livro livro) {
        this.dao.adiciona(livro);
    }

    public void atualiza(Livro livro) {
        this.dao.atualiza(livro);
    }

    public void remove(Livro livro) {
        this.dao.remove(livro);
    }
    public List<Livro> listaTodos() {
        return this.dao.listaTodos();
    }

}
package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.livraria.modelo.Autor;
@Stateless
public class AutorDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext
    private EntityManager em;

    private DAO<Autor> dao;

    @PostConstruct
    void init() {
        this.dao = new DAO<Autor>(this.em, Autor.class);
    }

    public void adiciona(Autor t) {
        dao.adiciona(t);
    }

    public void remove(Autor t) {
        dao.remove(t);
    }

    public void atualiza(Autor t) {
        dao.atualiza(t);
    }

    public List<Autor> listaTodos() {
        return dao.listaTodos();
    }

    public Autor buscaPorId(Integer id) {
        return dao.buscaPorId(id);
    }
}
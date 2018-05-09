package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private LivroDao livroDao;
	@Inject
	private AutorDao autorDao;

	private Livro livro = new Livro();

	private Integer autorId;

	private Integer livroId;

	private List<Livro> livros;
	
	private List<String> generos = Arrays.asList("Romance","Drama", "Ação");
	
	//Construtor


	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public List<Livro> getLivros() {

		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
		}

		return livros;
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}
	@Transactional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if (this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
			this.livros = livroDao.listaTodos();
		} else {
			livroDao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	public List<String> getGeneros() {
		return generos;
	}

	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}

	public void carregar(Livro livro) {
		System.out.println("Carregando livro " + livro.getTitulo());
		this.livro = livroDao.buscaPorId(livro.getId());
	}
	@Transactional
	public void remover(Livro livro) {
		System.out.println("Removendo livro " + livro.getTitulo());
		livroDao.remove(livro);
		this.livros.remove(livro);
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	public String formAutor() {
		System.out.println("Chamanda do formulário do Autor.");
		return "autor?faces-redirect=true";
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deveria começar com 1"));
		}

	}

	public void carregarLivroPelaId() {
		this.livro = livroDao.buscaPorId(livroId);
	}

	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale local) {
		// Tirando espaço do filtro

		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();
		System.out.println("Filtrado pelo " + textoDigitado + " Valor da coluna " + valorColuna);

		// o filtro é nulo ou vazio?
		if (textoDigitado == null || textoDigitado.equals("")) {
			return true;
		}

		// elemento da tabela é nulo?
		if (valorColuna == null) {
			return false;
		}
		try {

			// fazendo o parsing do filtro para converter para Double
			Double valorDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;
			// comparando os valores, compareTo devolve um valor negativo se o value é menor
			// do que o filtro
			return valorDigitado.compareTo(precoColuna) < 0;
		} catch (NumberFormatException e) {
			// Usuário não diigitou
			return false;
		}
	}

}

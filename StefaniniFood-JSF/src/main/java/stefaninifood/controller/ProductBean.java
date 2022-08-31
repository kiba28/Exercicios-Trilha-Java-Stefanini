package stefaninifood.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import stefaninifood.dao.DAO;
import stefaninifood.model.Product;
import stefaninifood.util.Message;


@Getter
@Setter
@Named("productBean")
@ViewScoped
public class ProductBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Product product = new Product();
	private List<Product> products;

	@PostConstruct
	public void load() {
		this.products = new DAO<>(Product.class).listaTodos();
	}

	public void add() {
		try {

			if (this.product.getId() == null) {
				new DAO<Product>(Product.class).adiciona(product);
				clean();
				this.load();
				Message.info("Adicionado com sucesso.");
			} else {
				new DAO<Product>(Product.class).atualiza(product);
				clean();
				this.load();
				Message.info("Atualizado com sucesso.");
			}
		} catch (Exception e) {
			Message.error(e.getMessage());
		}
	}

	public void remove() {
		try {
			new DAO<Product>(Product.class).remove(product);
			load();
			Message.info(product.getName() + " foi removido");
		} catch (Exception e) {
			Message.error(e.getMessage());
		}
	}

	private void clean() {
		this.product = new Product();
	}

}

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
import stefaninifood.model.Pedido;
import stefaninifood.model.Product;
import stefaninifood.util.Message;

@Getter
@Setter
@Named("orderBean")
@ViewScoped
public class PedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Pedido pedido = new Pedido();
	private Integer quantity = 1;
	private Product product = new Product();
	private List<Product> products;

	@PostConstruct
	public void load() {
		this.products = new DAO<>(Product.class).listaTodos();
	}

	public void add() {
		new DAO<Pedido>(Pedido.class).adiciona(pedido);
		clean();
		this.load();
		Message.info("Pedido realizado com sucesso.");

	}

	public void addProduct() {
		this.product = new DAO<>(Product.class).buscaPorId(product.getId());
		System.out.println(product.toString());

		for (int i = 0; i < quantity; i++) {
			pedido.getProducts().add(product);
			pedido.setTotalPrice(pedido.getTotalPrice().add(product.getPrice()));
		}
		// calPrice();
	}

	public void removeProduct() {
		pedido.getProducts().remove(product);
	}

	private void clean() {
		this.pedido = new Pedido();
	}

}

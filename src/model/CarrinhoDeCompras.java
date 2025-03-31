package model;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras {
    private List<ItemCarrinho> itens = new ArrayList<>();
    private Cliente cliente;

    public CarrinhoDeCompras(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean adicionarProduto(Produto produto, int quantidade, double desconto) {
        if (produto == null || quantidade <= 0) {
            return false;
        }
        itens.add(new ItemCarrinho(produto, quantidade, desconto));
        return true;
    }

    public double obterValorTotal() {
        double total = 0;
        for (ItemCarrinho item : itens) {
            total += item.obterCusto();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Carrinho de ").append(cliente.getNome()).append(":\n");
        for (ItemCarrinho item : itens) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("Total: R$ ").append(obterValorTotal());
        return sb.toString();
    }

    // Getters e Setters
    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
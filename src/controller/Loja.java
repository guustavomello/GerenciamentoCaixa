package controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Produto;
import model.CarrinhoDeCompras;

public class Loja {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Produto> estoque = new ArrayList<>();
    private List<CarrinhoDeCompras> vendasFinalizadas = new ArrayList<>();
    private CarrinhoDeCompras vendaEmAndamento;

    public void cadastrarCliente(Cliente novo) {
        clientes.add(novo);
    }

    public Cliente pesquisarCliente(String nome) {
        for (Cliente c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        return null;
    }

    public void cadastrarProduto(Produto novo) {
        estoque.add(novo);
    }

    public Produto pesquisarProduto(int codigo) {
        for (Produto p : estoque) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    public boolean iniciarVenda(String nomeCliente) {
        Cliente cliente = pesquisarCliente(nomeCliente);
        if (cliente == null) {
            return false;
        }
        vendaEmAndamento = new CarrinhoDeCompras(cliente);
        return true;
    }

    public boolean adicionarProduto(int codigo, int quantidade, double desconto) {
        if (vendaEmAndamento == null) {
            return false;
        }
        Produto produto = pesquisarProduto(codigo);
        if (produto == null) {
            return false;
        }
        return vendaEmAndamento.adicionarProduto(produto, quantidade, desconto);
    }

    public double fecharVenda() {
        if (vendaEmAndamento == null) {
            return 0;
        }
        double total = vendaEmAndamento.obterValorTotal();
        vendasFinalizadas.add(vendaEmAndamento);
        vendaEmAndamento = null;
        return total;
    }

    public String exibirCarrinho() {
        if (vendaEmAndamento == null) {
            return "Nenhuma venda em andamento";
        }
        return vendaEmAndamento.toString();
    }
}
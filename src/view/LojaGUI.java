package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Loja;
import model.Cliente;
import model.Produto;
import java.awt.Font;
import java.awt.Canvas;

public class LojaGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtOpcao;
    private Loja loja = new Loja();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LojaGUI frame = new LojaGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LojaGUI() {
        setTitle("Sistema de Gerenciamento de Loja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 308, 519);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblMenu = new JLabel("MENU PRINCIPAL");
        lblMenu.setBounds(10, 0, 185, 41);
        contentPane.add(lblMenu);

        JLabel lblOpcoes = new JLabel("<html>1- Cadastrar cliente<br>2- Cadastrar produto<br>3- Iniciar venda<br>4- Adicionar produto ao carrinho<br>5- Exibir conteúdo do carrinho<br>6- Fechar venda<br>0- Sair</html>");
        lblOpcoes.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblOpcoes.setBounds(10, 11, 267, 255);
        contentPane.add(lblOpcoes);

        JLabel lblOpcao = new JLabel("Opção:");
        lblOpcao.setBounds(10, 277, 46, 14);
        contentPane.add(lblOpcao);

        txtOpcao = new JTextField();
        txtOpcao.setBounds(61, 271, 86, 20);
        contentPane.add(txtOpcao);
        txtOpcao.setColumns(10);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processarOpcao();
            }
        });
        btnOk.setBounds(157, 268, 89, 23);  // Corrigido para btnOk
        contentPane.add(btnOk);
    }

    private void processarOpcao() {
        try {
            int opcao = Integer.parseInt(txtOpcao.getText());
            txtOpcao.setText("");
            
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    cadastrarProduto();
                    break;
                case 3:
                    iniciarVenda();
                    break;
                case 4:
                    adicionarProdutoCarrinho();
                    break;
                case 5:
                    exibirCarrinho();
                    break;
                case 6:
                    fecharVenda();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Opção inválida!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um número válido!");
        }
    }

    private void cadastrarCliente() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do cliente:");
        if (nome == null || nome.trim().isEmpty()) return;
        
        String cpfStr = JOptionPane.showInputDialog(this, "Digite o CPF do cliente:");
        if (cpfStr == null || cpfStr.trim().isEmpty()) return;
        
        try {
            BigInteger cpf = new BigInteger(cpfStr);
            loja.cadastrarCliente(new Cliente(nome, cpf));
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "CPF inválido!");
        }
    }

    private void cadastrarProduto() {
        String codigoStr = JOptionPane.showInputDialog(this, "Digite o código do produto:");
        if (codigoStr == null || codigoStr.trim().isEmpty()) return;
        
        String valorStr = JOptionPane.showInputDialog(this, "Digite o valor do produto:");
        if (valorStr == null || valorStr.trim().isEmpty()) return;
        
        try {
            int codigo = Integer.parseInt(codigoStr);
            double valor = Double.parseDouble(valorStr);
            loja.cadastrarProduto(new Produto(codigo, valor));
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores inválidos!");
        }
    }

    private void iniciarVenda() {
        String nomeCliente = JOptionPane.showInputDialog(this, "Digite o nome do cliente:");
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) return;
        
        boolean sucesso = loja.iniciarVenda(nomeCliente);
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Venda iniciada para " + nomeCliente);
        } else {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
        }
    }

    private void adicionarProdutoCarrinho() {
        String codigoStr = JOptionPane.showInputDialog(this, "Digite o código do produto:");
        if (codigoStr == null || codigoStr.trim().isEmpty()) return;
        
        String quantidadeStr = JOptionPane.showInputDialog(this, "Digite a quantidade:");
        if (quantidadeStr == null || quantidadeStr.trim().isEmpty()) return;
        
        String descontoStr = JOptionPane.showInputDialog(this, "Digite o desconto (%):");
        if (descontoStr == null || descontoStr.trim().isEmpty()) descontoStr = "0";
        
        try {
            int codigo = Integer.parseInt(codigoStr);
            int quantidade = Integer.parseInt(quantidadeStr);
            double desconto = Double.parseDouble(descontoStr);
            
            boolean sucesso = loja.adicionarProduto(codigo, quantidade, desconto);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Produto adicionado ao carrinho!");
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado ou venda não iniciada!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores inválidos!");
        }
    }

    private void exibirCarrinho() {
        String carrinho = loja.exibirCarrinho();
        JOptionPane.showMessageDialog(this, carrinho);
    }

    private void fecharVenda() {
        double total = loja.fecharVenda();
        JOptionPane.showMessageDialog(this, "Venda finalizada! Total: R$ " + total);
    }
}
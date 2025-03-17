package br.ufrpe.supermercado.interfacegrafica;

import br.ufrpe.supermercado.negocio.Facade;
import javax.swing.*;
import java.awt.*;

public class SupermercadoInterface extends JFrame {
    private final Facade fachada;

    public SupermercadoInterface() {
        fachada = new Facade();
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Supermercado");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        JButton btnCliente = new JButton("Área Cliente");
        JButton btnFuncionario = new JButton("Área Funcionário");
        JButton btnProduto = new JButton("Área Produto");
        JButton btnVenda = new JButton("Área Venda");
        JButton btnSair = new JButton("Sair");

        btnCliente.addActionListener(e -> abrirAreaCliente());
        btnFuncionario.addActionListener(e -> abrirAreaFuncionario());
        btnProduto.addActionListener(e -> abrirAreaProduto());
        btnVenda.addActionListener(e -> abrirAreaVenda());
        btnSair.addActionListener(e -> System.exit(0));

        add(btnCliente);
        add(btnFuncionario);
        add(btnProduto);
        add(btnVenda);
        add(btnSair);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void abrirAreaCliente() {
        String[] options = {"Cadastrar Cliente", "Listar Clientes", "Remover Cliente"};
        int escolha = JOptionPane.showOptionDialog(this, "Escolha uma opção", "Área Cliente",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (escolha) {
            case 0 -> cadastrarCliente();
            case 1 -> fachada.listarClientes();
            case 2 -> removerCliente();
        }
    }

    private void cadastrarCliente() {
        String nome = JOptionPane.showInputDialog("Digite o nome do cliente:");
        String cpf = JOptionPane.showInputDialog("Digite o CPF (apenas números):");
        String ano = JOptionPane.showInputDialog("Digite o ano de nascimento (yyyy):");
        String mes = JOptionPane.showInputDialog("Digite o mês de nascimento (mm):");
        String dia = JOptionPane.showInputDialog("Digite o dia de nascimento (dd):");

        try {
            fachada.cadastrarCliente(nome, cpf, Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private void removerCliente() {
        String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente a ser removido:");
        JOptionPane.showMessageDialog(this, fachada.removerCliente(cpf));
    }

    private void abrirAreaFuncionario() {
        String[] options = {"Cadastrar Funcionário", "Listar Funcionários", "Remover Funcionário"};
        int escolha = JOptionPane.showOptionDialog(this, "Escolha uma opção", "Área Funcionário",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (escolha) {
            case 0 -> cadastrarFuncionario();
            case 1 -> fachada.listarFuncionarios();
            case 2 -> removerFuncionario();
        }
    }

    private void cadastrarFuncionario() {
        String nome = JOptionPane.showInputDialog("Digite o nome do funcionário:");
        String cpf = JOptionPane.showInputDialog("Digite o CPF (apenas números):");
        String ano = JOptionPane.showInputDialog("Digite o ano de nascimento (yyyy):");
        String mes = JOptionPane.showInputDialog("Digite o mês de nascimento (mm):");
        String dia = JOptionPane.showInputDialog("Digite o dia de nascimento (dd):");

        try {
            fachada.cadastrarFuncionario(nome, cpf, Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    private void removerFuncionario() {
        String cpf = JOptionPane.showInputDialog("Digite o CPF do funcionário a ser removido:");
        JOptionPane.showMessageDialog(this, fachada.removerFuncionario(cpf));
    }

    private void abrirAreaProduto() {
        String[] options = {"Cadastrar Produto", "Listar Produtos", "Remover Produto"};
        int escolha = JOptionPane.showOptionDialog(this, "Escolha uma opção", "Área Produto",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (escolha) {
            case 0 -> cadastrarProduto();
            case 1 -> fachada.listarProdutos();
            case 2 -> removerProduto();
        }
    }

    private void cadastrarProduto() {
        String ean = JOptionPane.showInputDialog("Digite o código EAN do produto:");
        String nome = JOptionPane.showInputDialog("Digite o nome do produto:");
        String preco = JOptionPane.showInputDialog("Digite o preço do produto:");
        String quantidade = JOptionPane.showInputDialog("Digite a quantidade em estoque:");

        try {
            fachada.cadastrarProduto(ean, nome, Double.parseDouble(preco), Integer.parseInt(quantidade));
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    private void removerProduto() {
        String nomeProduto = JOptionPane.showInputDialog("Digite o nome do produto a ser removido:");
        JOptionPane.showMessageDialog(this, fachada.removerProduto(nomeProduto));
    }

    private void realizarVenda() {
        String cpfCliente = JOptionPane.showInputDialog("Digite o CPF do Cliente:");
        String cpfVendedor = JOptionPane.showInputDialog("Digite o CPF do Vendedor:");
        String nomeProduto = JOptionPane.showInputDialog("Digite o Nome do Produto:");
        String quantidadeStr = JOptionPane.showInputDialog("Digite a Quantidade:");

        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            String resultado = fachada.realizarVenda(cpfCliente, cpfVendedor, nomeProduto, quantidade);
            JOptionPane.showMessageDialog(this, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida. Insira um número inteiro.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar venda: " + e.getMessage());
        }
    }

    private void abrirAreaVenda() {
        String[] options = {"Realizar Venda", "Listar Vendas"};
        int escolha = JOptionPane.showOptionDialog(this, "Escolha uma opção", "Área Venda",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (escolha) {
            case 0 -> realizarVenda();
            case 1 -> fachada.listarVendas();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SupermercadoInterface::new);
    }
}

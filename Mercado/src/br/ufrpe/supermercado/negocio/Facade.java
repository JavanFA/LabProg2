package br.ufrpe.supermercado.negocio;

import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrpe.supermercado.dados.cliente.RepositorioArrayClientes;
import br.ufrpe.supermercado.dados.funcionario.RepositorioArrayFuncionarios;
import br.ufrpe.supermercado.dados.produto.RepositorioArrayProdutos;
import br.ufrpe.supermercado.dados.venda.RepositorioArrayVendas;
import br.ufrpe.supermercado.excecoes.CampoVazioExcecao;
import br.ufrpe.supermercado.excecoes.CpfInvalidoExcecao;

public class Facade {

    private RepositorioArrayVendas repositorioVendas;
    private RepositorioArrayClientes repositorioClientes;
    private RepositorioArrayFuncionarios repositorioFuncionarios;
    private RepositorioArrayProdutos repositorioProdutos;

    public Facade() {
        this.repositorioClientes = new RepositorioArrayClientes();
        this.repositorioFuncionarios = new RepositorioArrayFuncionarios();
        this.repositorioProdutos = new RepositorioArrayProdutos();
        this.repositorioVendas = new RepositorioArrayVendas();
    }

    // ================= CLIENTES =================

    public String cadastrarCliente(String nome, String cpf, int ano, int mes, int dia) {
        try {
            LocalDate data = LocalDate.of(ano, mes, dia);
            Cliente cliente = new Cliente();
            cliente.setNomeCompleto(nome);
            cliente.setCPF(cpf);
            cliente.setDataNascimento(data);

            repositorioClientes.inserir(cliente);
            return "Cliente " + cliente.getNomeCompleto() + " cadastrado com sucesso!";

        } catch (CpfInvalidoExcecao ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao cadastrar o cliente devido a um CPF inválido.";
        }
    }

    public String removerCliente(String cpf) {
        if (repositorioClientes.buscarPorCPF(cpf)) {
            repositorioClientes.remover(cpf);
            return "Cliente de CPF " + cpf + " foi removido com sucesso!";
        } else {
            return "CPF incorreto ou Cliente não está cadastrado!";
        }
    }

    public void listarClientes() {
        repositorioClientes.listarClientes();
    }

    public Cliente buscarClientePorCPF(String cpf) {
        return repositorioClientes.buscarClientePorCPF(cpf);
    }

    // ================= FUNCIONÁRIOS =================

    public String cadastrarFuncionario(String nome, String cpf, int ano, int mes, int dia) {
        try {
            LocalDate data = LocalDate.of(ano, mes, dia);
            Funcionario funcionario = new Funcionario();
            funcionario.setNomeCompleto(nome);
            funcionario.setCPF(cpf);
            funcionario.setDataNascimento(data);

            repositorioFuncionarios.inserir(funcionario);
            return "Funcionário " + funcionario.getNomeCompleto() + " cadastrado com sucesso!";

        } catch (CpfInvalidoExcecao ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao cadastrar o funcionário devido a um CPF inválido.";
        }
    }

    public String removerFuncionario(String cpf) {
        if (repositorioFuncionarios.buscarPorCPF(cpf)) {
            repositorioFuncionarios.remover(cpf);
            return "Funcionário de CPF " + cpf + " foi removido com sucesso!";
        } else {
            return "CPF incorreto ou Funcionário não está cadastrado!";
        }
    }

    public void listarFuncionarios() {
        repositorioFuncionarios.listarFuncionarios();
    }

    public Funcionario buscarFuncionarioPorCPF(String cpf) {
        return repositorioFuncionarios.buscarFuncionarioPorCPF(cpf);
    }

    // ================= PRODUTOS =================

    public String cadastrarProduto(String ean, String nomeProduto, double preco, int qntEmEstoque) {
        try {
            if (nomeProduto.trim().isEmpty()) {
                throw new CampoVazioExcecao("O nome do produto não pode estar vazio.");
            }
            if (preco <= 0) {
                throw new CampoVazioExcecao("O preço do produto deve ser maior que zero.");
            }

            Produto produto = new Produto();
            produto.setEan(ean);
            produto.setNomeProduto(nomeProduto);
            produto.setPreco(preco);
            produto.setQuantidadeEmEstoque(qntEmEstoque);

            repositorioProdutos.inserir(produto);
            return "Produto " + produto.getNomeProduto() + " cadastrado com sucesso!";

        } catch (CampoVazioExcecao ex) {
            return "Erro ao cadastrar produto: " + ex.getMessage();
        }
    }

    public Produto buscarProdutoPorNome(String nomeProduto) {
        return repositorioProdutos.buscarProdutoPorNome(nomeProduto);
    }

    public String removerProduto(String nomeProduto) {
        if (repositorioProdutos.buscarPorNomeProduto(nomeProduto)) {
            repositorioProdutos.remover(nomeProduto);
            return "Produto " + nomeProduto + " foi removido com sucesso!";
        } else {
            return "Nome incorreto ou Produto não está cadastrado!";
        }
    }

    public void listarProdutos() {
        repositorioProdutos.listarProdutos();
    }

    // ================= VENDAS =================

    public String realizarVenda(String cpfCliente, String cpfVendedor, String nomeProduto, int quantidade) {
        Cliente cliente = buscarClientePorCPF(cpfCliente);
        Funcionario vendedor = buscarFuncionarioPorCPF(cpfVendedor);
        Produto produto = buscarProdutoPorNome(nomeProduto);

        if (cliente == null) {
            return "Cliente não encontrado.";
        }

        if (vendedor == null) {
            return "Vendedor não encontrado.";
        }

        if (produto == null) {
            return "Produto não encontrado.";
        }

        if (produto.getQuantidadeEmEstoque() < quantidade) {
            return "Estoque insuficiente para o produto: " + produto.getNomeProduto();
        }

        produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - quantidade);

        Venda novaVenda = new Venda();
        novaVenda.setCliente(cliente);
        novaVenda.setVendedor(vendedor);
        novaVenda.setProduto(produto);
        novaVenda.setQuantidade(quantidade);
        novaVenda.setValorTotal(produto.getPreco() * quantidade);
        novaVenda.setDataCompra(new Date());

        repositorioVendas.registrarVenda(novaVenda);
        return "Venda realizada com sucesso!";
    }

    public void listarVendas() {
        repositorioVendas.listarVendas();
    }
}

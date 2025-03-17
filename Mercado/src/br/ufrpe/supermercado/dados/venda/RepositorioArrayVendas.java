package br.ufrpe.supermercado.dados.venda;

import br.ufrpe.supermercado.negocio.Venda;
import br.ufrpe.supermercado.util.ArquivoUtil;
import br.ufrpe.supermercado.excecoes.CpfInvalidoExcecao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RepositorioArrayVendas implements IRepositorioVendas {

    private int tamanhoAtual;
    private Venda[] vendas; // Array de vendas
    private static final String PASTA_DATA = "data"; // Pasta para salvar os arquivos
    private static final String CAMINHO_ARQUIVO = PASTA_DATA + "/vendas.txt"; // Caminho do arquivo

    public RepositorioArrayVendas() {
        this.vendas = new Venda[100]; // Inicializa o array com tamanho 100
        this.tamanhoAtual = 0; // Inicializa o tamanho atual como 0
        criarPastaSeNaoExistir(); // Cria a pasta "data" se ela não existir
        carregarDados(); // Carrega os dados ao iniciar
    }

    // Método para criar a pasta "data" se ela não existir
    private void criarPastaSeNaoExistir() {
        File pasta = new File(PASTA_DATA);
        if (!pasta.exists()) {
            pasta.mkdir(); // Cria a pasta
        }
    }

    // Método para carregar dados do arquivo
    private void carregarDados() {
        String dados = ArquivoUtil.carregarDeArquivo(CAMINHO_ARQUIVO);
        if (dados != null && !dados.isEmpty()) {
            String[] linhas = dados.split("\n"); // Divide o arquivo em linhas
            for (String linha : linhas) {
                String[] partes = linha.split(";"); // Divide cada linha em partes
                if (partes.length == 6) { // Verifica se a linha está no formato correto
                    Venda venda = new Venda();
                    try {
                        venda.getCliente().setCPF(partes[0]); // CPF do Cliente
                        venda.getVendedor().setCPF(partes[1]); // CPF do Vendedor
                    } catch (CpfInvalidoExcecao e) {
                        System.err.println("CPF inválido encontrado no arquivo: " + partes[0] + " ou " + partes[1]);
                        continue; // Ignora esta venda e passa para a próxima
                    }
                    venda.getProduto().setEan(partes[2]); // EAN do Produto
                    venda.setQuantidade(Integer.parseInt(partes[3])); // Quantidade
                    venda.setValorTotal(Double.parseDouble(partes[4])); // Valor Total
                    try {
                        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
                        Date dataCompra = formatoData.parse(partes[5]); // Data da Compra
                        venda.setDataCompra(dataCompra);
                    } catch (Exception e) {
                        System.err.println("Erro ao carregar data da venda: " + partes[5]);
                    }
                    registrarVenda(venda); // Registra a venda no repositório
                }
            }
        }
    }

    // Método para salvar dados no arquivo
    private void salvarDados() {
        StringBuilder dados = new StringBuilder();
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < tamanhoAtual; i++) {
            Venda venda = vendas[i];
            dados.append(venda.getCliente().getCPF()).append(";") // CPF do Cliente
                 .append(venda.getVendedor().getCPF()).append(";") // CPF do Vendedor
                 .append(venda.getProduto().getEan()).append(";") // EAN do Produto
                 .append(venda.getQuantidade()).append(";") // Quantidade
                 .append(venda.getValorTotal()).append(";") // Valor Total
                 .append(formatoData.format(venda.getDataCompra())).append("\n"); // Data da Compra
        }
        ArquivoUtil.salvarEmArquivo(CAMINHO_ARQUIVO, dados.toString()); // Salva no arquivo
    }

    private void redimensionarArray() {
        Venda[] novoArray = new Venda[vendas.length * 2];
        for (int i = 0; i < vendas.length; i++) {
            novoArray[i] = vendas[i];
        }
        vendas = novoArray;
    }

    @Override
    public void registrarVenda(Venda venda) {
        if (tamanhoAtual == vendas.length) {
            redimensionarArray();
        }
        vendas[tamanhoAtual] = venda;
        tamanhoAtual++;
        salvarDados(); // Salva os dados após registrar uma venda
    }

    @Override
    public void listarVendas() {
        for (int i = 0; i < tamanhoAtual; i++) {
            System.out.println(vendas[i]);
        }
    }
}
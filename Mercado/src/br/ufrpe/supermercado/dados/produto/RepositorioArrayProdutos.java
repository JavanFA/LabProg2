package br.ufrpe.supermercado.dados.produto;

import br.ufrpe.supermercado.negocio.Produto;
import br.ufrpe.supermercado.util.ArquivoUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RepositorioArrayProdutos implements IRepositorioProdutos {

    private Map<String, Produto> produtos;
    private static final String PASTA_DATA = "data"; // Pasta para salvar os arquivos
    private static final String CAMINHO_ARQUIVO = PASTA_DATA + "/produtos.txt"; // Caminho do arquivo

    public RepositorioArrayProdutos() {
        this.produtos = new HashMap<>();
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
                if (partes.length == 4) { // Verifica se a linha está no formato correto
                    Produto produto = new Produto();
                    produto.setEan(partes[0]); // EAN
                    produto.setNomeProduto(partes[1]); // Nome do Produto
                    produto.setPreco(Double.parseDouble(partes[2])); // Preço
                    produto.setQuantidadeEmEstoque(Integer.parseInt(partes[3])); // Quantidade em Estoque
                    inserir(produto); // Insere o produto no repositório
                }
            }
        }
    }

    // Método para salvar dados no arquivo
    private void salvarDados() {
        StringBuilder dados = new StringBuilder();
        for (Produto produto : produtos.values()) {
            dados.append(produto.getEan()).append(";") // EAN
                 .append(produto.getNomeProduto()).append(";") // Nome do Produto
                 .append(produto.getPreco()).append(";") // Preço
                 .append(produto.getQuantidadeEmEstoque()).append("\n"); // Quantidade em Estoque
        }
        ArquivoUtil.salvarEmArquivo(CAMINHO_ARQUIVO, dados.toString()); // Salva no arquivo
    }

    @Override
    public void inserir(Produto produto) {
        produtos.put(produto.getEan(), produto);
        salvarDados(); // Salva os dados após inserir um produto
    }

    @Override
    public void listarProdutos() {
        for (Produto produto : produtos.values()) {
            System.out.println(
                "Código de Barras: " + produto.getEan() + "\n" +
                "Produto: " + produto.getNomeProduto() + "\n" +
                "Valor: R$ " + produto.getPreco() + "\n" +
                "Quantidade em Estoque: " + produto.getQuantidadeEmEstoque() + "\n"
            );
        }
    }

    @Override
    public void remover(String ean) {
        produtos.remove(ean);
        salvarDados(); // Salva os dados após remover um produto
    }

    @Override
    public boolean buscarPorNomeProduto(String nomeProduto) {
        return produtos.values().stream()
                .anyMatch(produto -> produto.getNomeProduto().equalsIgnoreCase(nomeProduto));
    }

    // Novo método para buscar e retornar o objeto Produto pelo nome
    public Produto buscarProdutoPorNome(String nomeProduto) {
        for (Produto produto : produtos.values()) {
            if (produto.getNomeProduto().equalsIgnoreCase(nomeProduto)) {
                return produto;
            }
        }
        return null;
    }

    // Novo método para buscar e retornar o objeto Produto pelo EAN
    public Produto buscarProdutoPorEAN(String ean) {
        return produtos.getOrDefault(ean, null);
    }
}

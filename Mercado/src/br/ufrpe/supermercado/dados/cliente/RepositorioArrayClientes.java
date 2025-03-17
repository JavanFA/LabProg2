package br.ufrpe.supermercado.dados.cliente;

import br.ufrpe.supermercado.negocio.Cliente;
import br.ufrpe.supermercado.util.ArquivoUtil;
import br.ufrpe.supermercado.excecoes.CpfInvalidoExcecao;

import java.io.File;
import java.time.LocalDate;

public class RepositorioArrayClientes implements IRepositorioClientes {

    private Cliente[] clientes;
    private int tamanhoAtual;
    private static final String PASTA_DATA = "data"; // Pasta para salvar os arquivos
    private static final String CAMINHO_ARQUIVO = PASTA_DATA + "/clientes.txt"; // Caminho do arquivo

    public RepositorioArrayClientes() {
        this.clientes = new Cliente[100];
        this.tamanhoAtual = 0;
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
                if (partes.length == 3) { // Verifica se a linha está no formato correto
                    Cliente cliente = new Cliente();
                    cliente.setNomeCompleto(partes[0]); // Nome
                    try {
                        cliente.setCPF(partes[1]); // CPF (pode lançar CpfInvalidoExcecao)
                    } catch (CpfInvalidoExcecao e) {
                        System.err.println("CPF inválido encontrado no arquivo: " + partes[1]);
                        continue; // Ignora este cliente e passa para o próximo
                    }
                    cliente.setDataNascimento(LocalDate.parse(partes[2])); // Data de Nascimento
                    inserir(cliente); // Insere o cliente no repositório
                }
            }
        }
    }

    // Método para salvar dados no arquivo
    private void salvarDados() {
        StringBuilder dados = new StringBuilder();
        for (int i = 0; i < tamanhoAtual; i++) {
            Cliente cliente = clientes[i];
            dados.append(cliente.getNomeCompleto()).append(";") // Nome
                 .append(cliente.getCPF()).append(";") // CPF
                 .append(cliente.getDataNascimento()).append("\n"); // Data de Nascimento
        }
        ArquivoUtil.salvarEmArquivo(CAMINHO_ARQUIVO, dados.toString()); // Salva no arquivo
    }

    private void redimensionarArray() {
        Cliente[] novoArray = new Cliente[clientes.length * 2];
        for (int i = 0; i < clientes.length; i++) {
            novoArray[i] = clientes[i];
        }
        clientes = novoArray;
    }

    @Override
    public void inserir(Cliente cliente) {
        if (tamanhoAtual == clientes.length) {
            redimensionarArray();
        }
        clientes[tamanhoAtual] = cliente;
        tamanhoAtual++;
        salvarDados(); // Salva os dados após inserir um cliente
    }

    @Override
    public void listarClientes() {
        for (int i = 0; i < tamanhoAtual; i++) {
            System.out.println(clientes[i]);
        }
    }

    @Override
    public void remover(String cpf) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (clientes[i].getCPF().equals(cpf)) {
                clientes[i] = clientes[tamanhoAtual - 1];
                tamanhoAtual--;
                salvarDados(); // Salva os dados após remover um cliente
                break;
            }
        }
    }

    @Override
    public boolean buscarPorCPF(String cpfConsulta) {
        if (cpfConsulta.length() != 11) {
            return false;
        } else {
            for (int i = 0; i < tamanhoAtual; i++) {
                if (clientes[i].getCPF().equals(cpfConsulta)) {
                    return true;
                }
            }
            return false;
        }
    }
    public Cliente buscarClientePorCPF(String cpfConsulta) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (clientes[i].getCPF().equals(cpfConsulta)) {
                return clientes[i];
            }
        }
        return null;
    }
    
}
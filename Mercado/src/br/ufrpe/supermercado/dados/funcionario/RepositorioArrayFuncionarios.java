package br.ufrpe.supermercado.dados.funcionario;

import br.ufrpe.supermercado.negocio.Funcionario;
import br.ufrpe.supermercado.util.ArquivoUtil;
import br.ufrpe.supermercado.excecoes.CpfInvalidoExcecao;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RepositorioArrayFuncionarios implements IRepositorioFuncionarios {

    private Map<String, Funcionario> funcionarios;
    private static final String PASTA_DATA = "data"; // Pasta para salvar os arquivos
    private static final String CAMINHO_ARQUIVO = PASTA_DATA + "/funcionarios.txt"; // Caminho do arquivo

    public RepositorioArrayFuncionarios() {
        this.funcionarios = new HashMap<>();
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
                    Funcionario funcionario = new Funcionario();
                    funcionario.setNomeCompleto(partes[0]); // Nome
                    try {
                        funcionario.setCPF(partes[1]); // CPF (pode lançar CpfInvalidoExcecao)
                    } catch (CpfInvalidoExcecao e) {
                        System.err.println("CPF inválido encontrado no arquivo: " + partes[1]);
                        continue; // Ignora este funcionário e passa para o próximo
                    }
                    funcionario.setDataNascimento(LocalDate.parse(partes[2])); // Data de Nascimento
                    inserir(funcionario); // Insere o funcionário no repositório
                }
            }
        }
    }

    // Método para salvar dados no arquivo
    private void salvarDados() {
        StringBuilder dados = new StringBuilder();
        for (Funcionario funcionario : funcionarios.values()) {
            dados.append(funcionario.getNomeCompleto()).append(";") // Nome
                 .append(funcionario.getCPF()).append(";") // CPF
                 .append(funcionario.getDataNascimento()).append("\n"); // Data de Nascimento
        }
        ArquivoUtil.salvarEmArquivo(CAMINHO_ARQUIVO, dados.toString()); // Salva no arquivo
    }

    @Override
    public void inserir(Funcionario funcionario) {
        funcionarios.put(funcionario.getCPF(), funcionario);
        salvarDados(); // Salva os dados após inserir um funcionário
    }

    @Override
    public boolean buscarPorCPF(String cpfConsulta) {
        return funcionarios.containsKey(cpfConsulta);
    }

    // Novo método para buscar e retornar o objeto Funcionario
    public Funcionario buscarFuncionarioPorCPF(String cpf) {
        return funcionarios.get(cpf);
    }

    @Override
    public void remover(String cpf) {
        funcionarios.remove(cpf);
        salvarDados(); // Salva os dados após remover um funcionário
    }

    @Override
    public void listarFuncionarios() {
        for (Funcionario funcionario : funcionarios.values()) {
            System.out.println("Nome funcionário: " + funcionario.getNomeCompleto() +
                               "\nCPF: " + funcionario.getCPF() +
                               "\nData de Nascimento: " + funcionario.getDataNascimento() + "\n");
        }
    }
}

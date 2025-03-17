import java.util.Scanner;
import br.ufrpe.supermercado.negocio.Facade;

public class Teste {
    public static void main(String[] args) throws Exception {
        System.out.println("Bem-vindo ao mercadinho! \n################################");

        boolean menu = true;
        Facade fachada = new Facade();
        Scanner scan = new Scanner(System.in);

        while (menu) {
            System.out.println(
                "Escolha uma opção: \n1- Área Cliente \n2- Área Funcionário \n3- Área Produto \n4- Vendas \n5- Sair");
            String option = scan.nextLine();

            switch (option) {
                case "1":
                    areaCliente(fachada, scan);
                    break;
                case "2":
                    areaFuncionario(fachada, scan);
                    break;
                case "3":
                    areaProduto(fachada, scan);
                    break;
                case "4":
                    areaVendas(fachada, scan);
                    break;
                case "5":
                    menu = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

            System.out.println("Pressione uma tecla para continuar");
            scan.nextLine();
        }

        scan.close();
        System.out.println("O programa se encerrou!");
    }

    private static void areaCliente(Facade fachada, Scanner scan) {
        System.out.println("Escolha uma opção: \n1- Cadastro de cliente \n2- Listar clientes \n3- Remover cliente\n4- Voltar");
        String option = scan.nextLine();
        switch (option) {
            case "1":
                System.out.print("Nome: ");
                String nome = scan.nextLine();
                System.out.print("CPF: ");
                String cpf = scan.nextLine();
                System.out.print("Ano de Nascimento: ");
                int ano = Integer.parseInt(scan.nextLine());
                System.out.print("Mês de Nascimento: ");
                int mes = Integer.parseInt(scan.nextLine());
                System.out.print("Dia de Nascimento: ");
                int dia = Integer.parseInt(scan.nextLine());
                System.out.println(fachada.cadastrarCliente(nome, cpf, ano, mes, dia));
                break;
            case "2":
                fachada.listarClientes();
                break;
            case "3":
                System.out.print("CPF do cliente a remover: ");
                System.out.println(fachada.removerCliente(scan.nextLine()));
                break;
            default:
                break;
        }
    }

    private static void areaFuncionario(Facade fachada, Scanner scan) {
        System.out.println("Escolha uma opção: \n1- Cadastro de funcionário \n2- Listar funcionários \n3- Remover funcionário\n4- Voltar");
        String option = scan.nextLine();
        switch (option) {
            case "1":
                System.out.print("Nome: ");
                String nome = scan.nextLine();
                System.out.print("CPF: ");
                String cpf = scan.nextLine();
                System.out.print("Ano de Nascimento: ");
                int ano = Integer.parseInt(scan.nextLine());
                System.out.print("Mês de Nascimento: ");
                int mes = Integer.parseInt(scan.nextLine());
                System.out.print("Dia de Nascimento: ");
                int dia = Integer.parseInt(scan.nextLine());
                System.out.println(fachada.cadastrarFuncionario(nome, cpf, ano, mes, dia));
                break;
            case "2":
                fachada.listarFuncionarios();
                break;
            case "3":
                System.out.print("CPF do funcionário a remover: ");
                System.out.println(fachada.removerFuncionario(scan.nextLine()));
                break;
            default:
                break;
        }
    }

    private static void areaProduto(Facade fachada, Scanner scan) {
        System.out.println("Escolha uma opção: \n1- Cadastro de produto \n2- Listar produtos \n3- Remover produto\n4- Voltar");
        String option = scan.nextLine();
        switch (option) {
            case "1":
                System.out.print("EAN: ");
                String ean = scan.nextLine();
                System.out.print("Nome do produto: ");
                String nome = scan.nextLine();
                System.out.print("Preço: ");
                double preco = Double.parseDouble(scan.nextLine());
                System.out.print("Quantidade em estoque: ");
                int quantidade = Integer.parseInt(scan.nextLine());
                System.out.println(fachada.cadastrarProduto(ean, nome, preco, quantidade));
                break;
            case "2":
                fachada.listarProdutos();
                break;
            case "3":
                System.out.print("Nome do produto a remover: ");
                System.out.println(fachada.removerProduto(scan.nextLine()));
                break;
            default:
                break;
        }
    }

    private static void areaVendas(Facade fachada, Scanner scan) {
        System.out.println("Escolha uma opção: \n1- Nova venda \n2- Listar vendas \n3- Voltar");
        String option = scan.nextLine();
        switch (option) {
            case "1":
                System.out.print("CPF do Cliente: ");
                String cpfCliente = scan.nextLine();
                System.out.print("CPF do Vendedor: ");
                String cpfVendedor = scan.nextLine();
                System.out.print("Nome do Produto: ");
                String nomeProduto = scan.nextLine();
                System.out.print("Quantidade: ");
                int quantidade = Integer.parseInt(scan.nextLine());
                System.out.println(fachada.realizarVenda(cpfCliente, cpfVendedor, nomeProduto, quantidade));
                break;
            case "2":
                fachada.listarVendas();
                break;
            default:
                break;
        }
    }
}

package usuarios;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Sistema {
    private final Scanner sc = new Scanner(System.in);

    public int inicio() {

        System.out.println("----------------------------------------");
        System.out.println("Digite: ");
        System.out.println("- 1 para logar");
        System.out.println("- 2 para registrar");
        System.out.println("- 3 para sair");
        System.out.println("----------------------------------------");

        return obterResposta(0);
    }

    public int promptLogin() {

        System.out.println("----------------------------------------");
        System.out.println("Digite: ");
        System.out.println("- 1 para logar como cliente");
        System.out.println("- 2 para logar como funcionario");
        System.out.println("- 3 para logar como gerente");
        System.out.println("----------------------------------------");

        return obterResposta(0);
    }

    public void opcoesCliente(Cliente cliente){

        System.out.println("-----------------------------------------");
        System.out.println("Voce pode: ");
        System.out.println(cliente.listarMetodos());
        System.out.println("- 4 Deslogar\n");

        int acao =  obterRespostaPlus(0);
        // limpa buffer
        sc.nextLine();
        // Adiciona pet
        if (acao == 1){
            System.out.print("Insira o nome do pet: ");
            String nome = sc.nextLine().toLowerCase();

            System.out.print("Insira a especie do pet: ");
            String especie = sc.nextLine().toLowerCase();

            System.out.print("Insira a raca do pet ou sdr caso seja sem raca: ");
            String raca = sc.nextLine().toLowerCase();

            System.out.println();
            cliente.adicionarPet(nome, especie, raca);
        }
        // Marca banho
        else if (acao == 2){
            System.out.print("Insira o nome do pet que voce deseja marcar o banho: ");
            String nome = sc.nextLine().toLowerCase();

            cliente.marcaBanho(nome);
        }
        // Marca tosa
        else if (acao == 3){
            System.out.print("Insira o nome do pet que voce deseja marcar a tosa: ");
            String nome = sc.nextLine().toLowerCase();

            cliente.marcaTosa(nome);
        }

        if (acao != 4){
            opcoesCliente(cliente);
        }
    }

    public void opcoesFuncionario(Funcionario funcionario){
        System.out.println("----------------------------------------");
        System.out.println("Voce pode: ");
        System.out.println(funcionario.listarMetodos());
        System.out.println("- 4 Deslogar\n");

        int acao =  obterRespostaPlus(0);
        // limpa buffer
        sc.nextLine();
        // Ver pets em espera
        if (acao == 1) {
            System.out.print("Insira quantos pets voce quer ver: ");
            int quant = -1;

            while(true){
                try{
                    quant = sc.nextInt();

                    if (quant <= 0 || quant > 20){
                        System.out.println("\nResposta invalida");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e){
                    System.out.println("\nResposta invalida");
                    sc.nextLine();
                    System.out.print("Insira quantos pets voce quer ver: ");
                }
            }

            funcionario.checaPetsEmEspera(quant);
        }
        // Tosa pet
        else if (acao == 2){
            System.out.print("Insira o id da lista de pedidos em espera do pet para tosar: ");
            int id = -1;

            while(true){
                try{
                    id = sc.nextInt();
                    break;
                } catch (InputMismatchException e){
                    System.out.println("\nResposta invalida");
                    sc.nextLine();
                    System.out.print("Insira o id da lista de pedidos em espera do pet para tosar: ");
                }
            }

            funcionario.tosar(id);
        }
        // Da banho em pet
        else if (acao == 3){
            System.out.print("Insira o id da lista de pedidos em espera do pet para dar banho: ");
            int id = -1;

            while(true){
                try{
                    id = sc.nextInt();
                    break;
                } catch (InputMismatchException e){
                    System.out.println("\nResposta invalida");
                    sc.nextLine();
                    System.out.print("Insira o id da lista de pedidos em espera do pet para dar banho: ");
                }
            }

            funcionario.darBanho(id);
        }

        if  (acao != 4){
            opcoesFuncionario(funcionario);
        }
    }

    public void opcoesGerente(Gerente gerente){
        System.out.println("-----------------------------------------");
        System.out.println("Voce pode: ");
        System.out.println(gerente.listarMetodos());
        System.out.println("- 8 Deslogar\n");

        int acao =  obterRespostaPlusPlus(0);
        sc.nextLine();

        if (acao == 1){
            System.out.print("Insira quantos funcionarios voce quer ver: ");
            int quant = sc.nextInt();

            gerente.listarFuncionariosAtivos(quant);
        }

        else if (acao == 2){
            System.out.print("Insira quantos clientes voce quer ver: ");
            int quant = sc.nextInt();

            gerente.listarClientes(quant);
        }

        else if (acao == 3){
            System.out.println("Quantia de Funcionarios ativos: " + gerente.contarFuncionariosAtivos());
        }

        else if (acao == 4){
            System.out.println("Quantia de Clientes Registrados: " + gerente.contarClientes());
        }

        else if (acao == 5){
            System.out.println("Quantia de pedidos Concluidos: " + gerente.contarPedidosConcluidos());
        }
        else if (acao == 6){
            System.out.print("Escreva o email do funcionario para demitir: ");
            String email = sc.nextLine();

            gerente.demitirFuncionario(email);
        }

        else if (acao == 7){
            System.out.print("Escreva o nome do funcionario para contratar: ");
            String nome = sc.nextLine();

            System.out.print("Escreva o email do funcionario para contratar: ");
            String email = sc.nextLine();

            gerente.contratarFuncionario(nome, email);
        }
        if (acao != 8){
            opcoesGerente(gerente);
        }
    }

    public int obterResposta(int resposta) {
        while (resposta < 1 ||  resposta > 3) {
            try {
                System.out.print("Sua resposta: ");
                resposta = sc.nextInt();

                if (resposta > 3 || resposta < 1){
                    System.out.println("\nResposta invalida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nResposta invalida. Tente novamente.");
                sc.nextLine();
                resposta = 0;
            }
        }
        return resposta;
    }

    public int obterRespostaPlus(int resposta) {
        while (resposta < 1 || resposta > 4) {
            try {
                System.out.print("Sua resposta: ");
                resposta = sc.nextInt();

                if (resposta > 4 || resposta < 1){
                    System.out.println("\nResposta invalida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nResposta invalida. Tente novamente.");
                sc.nextLine();
                resposta = 0;
            }
        }
        return resposta;
    }
    public int obterRespostaPlusPlus(int resposta) {
        while (resposta < 1 || resposta > 8) {
            try {
                System.out.print("Sua resposta: ");
                resposta = sc.nextInt();

                if (resposta > 8 || resposta < 1){
                    System.out.println("\nResposta invalida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nResposta invalida. Tente novamente.");
                sc.nextLine();
                resposta = 0;
            }
        }
        return resposta;
    }
}

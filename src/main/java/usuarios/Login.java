package usuarios;

import usuarios.Usuario;
import usuarios.Cliente;
import usuarios.Gerente;

import main.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;



/*  Classe Login que lida com o login e registro de usuarios
 *  Criada com o intuito que na main alguem inicialize um usuário e use dessa função para puxar os dados do banco de dados
 * 
 *  Atributos:
 *  Nenhum
 * 
 *  Métodos: 
 * 
 *  Logar:
 *  Usa de scanner e faz prompts para o usuário (isso deve ser deixado para o front)
 * 
 *  LogarCliente, LogarFuncionario, LogarGerente. São os métodos direcionados para fazer uma busca só nos bancos de dados adequados
 *  Retorna nulo caso não consiga achar o Usuario no banco de dados  
 * 
 *  Registrar:
 *  Essa função só Registra Clientes, Funcionarios são contratados pelos Gerentes e Gerentes, no momento, são adicionados diretamente pelo banco de dados
 *  
 */

public class Login {
    private final Scanner sc = new Scanner(System.in);

    public Cliente logarCliente(){

    System.out.print("Digite o nome do cliente: ");
    String nome = sc.nextLine().trim();
    System.out.print("Digite o email do cliente: ");
    String email = sc.nextLine().trim();

    String buscaCliente = "SELECT nome FROM clientes WHERE email = ? AND nome = ?";
    
    try (Connection con = Database.getConnection();
         PreparedStatement psBusca = con.prepareStatement(buscaCliente)){
            
        psBusca.setString(1, email);
        psBusca.setString(2, nome);

        try (ResultSet rs = psBusca.executeQuery()) {
            if (rs.next()) {

                Cliente c = new Cliente();
                c.setEmail(email);
                c.setNome(nome);
                    
                return c;

                }
            else{
                System.out.println("\nCredenciais inválidas para cliente.");
                return null;
            }
            }
        } catch (SQLException e){
            //e.printStackTrace();
            return null;
        }
    }

    public Funcionario logarFuncionario(){
    System.out.print("Digite o nome do Funcionario: ");
    String nome = sc.nextLine();
    System.out.print("Digite o email do Funcionario: ");
    String email = sc.nextLine();


    String buscaFunc = "SELECT nome FROM funcionarios WHERE email = ? AND nome = ? AND ativo = 1 AND cargo = 'funcionario'";
    
    try (Connection con = Database.getConnection();
        PreparedStatement psBusca = con.prepareStatement(buscaFunc)){
            
        psBusca.setString(1, email);
        psBusca.setString(2, nome);

        try (ResultSet rs = psBusca.executeQuery()) {
            if (rs.next()) {
                Funcionario f = new Funcionario(nome, email);
                return f;

                }
                else{
                    System.out.println("\nCredenciais invalidas para funcionario.");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Gerente logarGerente() {
        System.out.print("Digite o nome do gerente: ");
        String nome = sc.nextLine();
        System.out.print("Digite o email do gerente: ");
        String email = sc.nextLine();


        String buscaGerente = "SELECT nome FROM funcionarios WHERE email = ? AND nome = ? AND cargo = 'gerente'";

        try (Connection con = Database.getConnection();
            PreparedStatement psBusca = con.prepareStatement(buscaGerente)){
            psBusca.setString(1, email);
            psBusca.setString(2, nome);
            ResultSet rs = psBusca.executeQuery();

            try (rs) {
                if (rs.next()) {
                    return new Gerente(nome, email);
                }
                else {
                    System.out.println("\nCredenciais invalidas para gerente.");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Registra clientes
    public Cliente registrar() {
        System.out.print("Digite o nome do cliente: ");
        String nome = sc.nextLine();
        System.out.print("Digite o email do cliente: ");
        String email = sc.nextLine();

        try {
            String validEmail = Usuario.checaEmail(email);
            return Cliente.criarEPersistir(nome, validEmail);
        } catch (UsuarioError e) {
            System.out.println(e.toString());
            System.out.println();
            return registrar();
        }
    }
}

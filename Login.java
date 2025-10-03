import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/* Classe que lida com o login e registro de usuarios
 * 
 *  Metodos: 
 * 
 *  Logar()
 *  Registrar()
 *  
 */

public class Login {
    
    // Pode ser tanto cliente quanto funcionário
    public Usuario logar(String nome, String email){
        // Checar primeiro se o existe alguem com esse email e nome em funcionarios e dps em clientes
        // dps printar "logou como (CLIENTE)/(FUNCIONARIO) NOME" return true
        // Caso nao ache nada, printe certifique suas credenciais ou tente se registrar novamente return false
        Scanner sc = new Scanner(System.in);
        int escolha = -1;

        System.out.println("Selecione 1 caso queira logar como cliente, 2 caso queira logar como funcionario e 3 caso queira logar como gerente");

        while(true){
            try {
                while (escolha != 1 && escolha != 2 && escolha != 3){
                    escolha = sc.nextInt();
                }
                break;
            } catch (InputMismatchException e){
                System.out.println("Resposta invalida. Tente Novamente.");
                sc.nextLine();
            }
        }

        switch(escolha){
            case 1:
                return logarCliente(nome, email);
            case 2:
                return logarFuncionario(nome, email);
            case 3:
                return logarGerente(nome, email); 
        }
    
        return null;
    }

    private Cliente logarCliente(String nome, String email){
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
                    System.out.println("Credenciais inválidas para cliente.");
                    return null;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private Funcionario logarFuncionario(String nome, String email){
    String buscaFunc = "SELECT nome FROM funcionarios WHERE email = ? AND nome = ?";
    
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
                    System.out.println("Credenciais invalidas para funcionario.");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Gerente logarGerente(String nome, String email){
    String buscaGerente = "SELECT nome FROM funcionarios WHERE email = ? AND nome = ? AND cargo = 'gerente'";
    
    try (Connection con = Database.getConnection();
        PreparedStatement psBusca = con.prepareStatement(buscaGerente)){
            
        psBusca.setString(1, email);
        psBusca.setString(2, nome);

        try (ResultSet rs = psBusca.executeQuery()) {
            if (rs.next()) {
                Gerente g = new Gerente();
                g.setNome(nome);
                g.setEmail(email);

                return g;
                }
                else{
                    System.out.println("Credenciais invalidas para gerente.");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // So registra clientes
    public Cliente registrar(String nome, String email){
        return Cliente.criarEPersistir(nome, email);
    }

}

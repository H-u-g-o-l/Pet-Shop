package src.main.java.usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* Classe Gerente criada com o intuito de obter informações mais gerais sobre o pet shop e seu funcionamento interior
 * 
 * Atributos:
 *  Nome e email (da classe pai)
 *  (posso adicionar nível de responsabilidade, gerentes de nível 1 e 2, 2 só interage com funcionários e 1 interage com eles + os gerentes de n. 2)
 * 
 *  Métodos:
 *  Construtor nulo (público), construtor com parametros privado (factory).
 *  Nessa Classe foi usado do método Factory para criar Gerentes mas isso não é necessariamente usado no código, só talvez com a implementação do nível do gerente
 *  
 *  listarFuncionariosAtivos(limite), recebe um inteiro de delimita o limite de quantos funcionários, que estão trabalhando, serão exibidos.
 * 
 *  contaFuncionariosAtivos(), só retorna a quantia de funcionários trabalhando
 * 
 *  listarClientes(limite), mesma coisa que listarFuncionariosAtivos
 *  contarClientes(), msm coisa que contar funcionários
 * 
 *  contarPedidosConcluidos, exibe a quantia de pedidos concluídos, pedidos concluídos são os serviços feitos, não necessariamente o pedido do cliente feito mas sim quem fez o que
 *  se x funcionário da banho, ele é registrado como alguém que fez isso, se y faz a tosa isso está registrado
 * 
 *  demitirFuncionario(email), torna o 'ativo' no banco de dados = 0, demitindo-o.
 * 
 *  contratarFuncionarios(nome, email), cria um funcionario no banco de dados
 * 
 *  toString, retorna nome e email
 * 
 *  listarMetodos, retorna uma string falando sobre o que um gerente faz
 */


public class Gerente extends Usuario implements Utilidades{

    public Gerente(){
        super(null, null);
    }

    private Gerente(String nome, String email, boolean persistido){
        super(nome, email);
    }

    public static Gerente criarEPersistir(String nome, String email){
        String inserirFunc = "INSERT INTO funcionarios (nome, email, cargo) VALUES (?, ?, ?)";

        try (Connection con = Database.getConnection();
            PreparedStatement psInserir = con.prepareStatement(inserirFunc)){
            
            psInserir.setString(1, nome);
            psInserir.setString(2, email);
            psInserir.setString(3, "gerente");

            return new Gerente(nome, email, true);

        } catch (SQLException e){
            String erro = e.getMessage();

            if (erro.contains("unique") || erro.contains("uniqueness")){
                System.out.println("Email ja cadastrado como funcionario: " + email + ".\nNao foi possivel criar o gerente.");
            }
            System.err.println(erro);
            e.printStackTrace();

            return null;
        }
    }

    // Listar funcionarios ativos
    public void listarFuncionariosAtivos(int lim){
        if (lim <= 0){
            System.out.println("Limite para listar errado, tente novamente");
            return;
        }

        String listaFuncionarios = "SELECT nome, email FROM funcionarios WHERE ativo = 1 ORDER BY id LIMIT ?";

        try (Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(listaFuncionarios)){

            ps.setInt(1, lim);

            try (ResultSet rs = ps.executeQuery()){
                boolean algum = false;
                while (rs.next()){
                    algum = true;
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");

                    System.out.println("Nome: " + nome + " Email: " + email);
                }

                if (!algum){
                    System.out.println("Nenhum funcionario ativo foi encontrado.");
                }
            }
        } catch (SQLException e){
            System.err.println("Erro ao listar funcionarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Ver quantos funcionarios tem trabalhando
    public int contarFuncionariosAtivos(){
        String contaFuncAtivos = "SELECT COUNT(*) FROM funcionarios WHERE active = 1";

        try (Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(contaFuncAtivos);
            ResultSet rs = ps.executeQuery()) {
            if (rs.next()){
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e){
            System.err.println("Erro ao contar Funcionarios ativos: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
 
    // Listar clientes
    public void listarClientes(int lim){
        if (lim <= 0){
            System.out.println("Quantidade para listar errada, tente novamente");
            return;
        }

        String listaClientes = "SELECT nome, email FROM clientes ORDER BY id LIMIT ?";

        try (Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(listaClientes)){

            ps.setInt(1, lim);

            try (ResultSet rs = ps.executeQuery()){
                boolean algum = false;
                while (rs.next()){
                    algum = true;
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");

                    System.out.println("Nome: " + nome + " Email: " + email);
                }

                if (!algum){
                    System.out.println("Nenhum cliente foi encontrado.");
                }
            }
        } catch (SQLException e){
            System.err.println("Erro ao listar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Ver quantos clientes estão registrados
    public int contarClientes(){
        String contaClientes = "SELECT COUNT(*) FROM clientes";

        try (Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(contaClientes);
            ResultSet rs = ps.executeQuery()) {
            if (rs.next()){
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e){
            e.getMessage();
            e.printStackTrace();
            return 0;
        }
    }
    
    // Quantos pedidos foram concluidos
    public int contarPedidosConcluidos(){
        String contaPedidosConcluidos = "SELECT COUNT(*) FROM pedidos_concluidos";

        try (Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(contaPedidosConcluidos);
            ResultSet rs = ps.executeQuery()) {
            if (rs.next()){
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e){
            e.getMessage();
            e.printStackTrace();
            return 0;
        }
    }
    
    // Demitir funcionario
    public void demitirFuncionario(String email){
        String demitir = "UPDATE funcionarios SET ativo = 0, demitido_em = CURRENT_TIMESTAMP where email = ?";

        try (Connection con = Database.getConnection();
            PreparedStatement psDem = con.prepareStatement(demitir)){
            
            psDem.setString(1, email);
            if (psDem.executeUpdate() == 0){
                System.out.println("Funcionario nao encontrado.");
            }
            System.out.println("Funcionario com o email: " + email + " foi demitido");
        } catch (SQLException e){
            System.err.println("Erro ao demitir funcionario com email: " + email + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Contratar funcionario
    public void contratarFuncionario(String nome, String email){
        String contratar = "INSERT INTO funcionarios (nome, email) VALUES (?, ?)";

        try (Connection con = Database.getConnection();
            PreparedStatement psCont = con.prepareStatement(contratar)){
            
            psCont.setString(1, nome);
            psCont.setString(2, email);

            psCont.executeUpdate();
        } catch (SQLException e){
            String erro = e.getMessage();

            if (erro.contains("unique") || erro.contains("uniqueness")){
                System.out.println("Um funcionario com esse email ja foi inserido");
            }

            System.err.println("Erro ao contratar: " + nome + " com email: " + email + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "Nome do Gerente: " + this.getNome() +
                "\nEmail do Gerente: " + this.getEmail();
    }

    @Override
    public String listarMetodos(){
        return "O gerente pode: Listar os funcionarios que estao na ativa, contar eles, contar os clientes registrado e demitir ou contratar um funcionario.\n";
    }
}

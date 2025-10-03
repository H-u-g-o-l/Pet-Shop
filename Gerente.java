import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* Classe encarregada de:
    - Ver quantos funcionarios tem
    - Quantos pedidos foram feitos e concluidos
    - Demitir e contratar Funcionarios
    - Ver quantos clientes estão registrados no pet_shop
    - Classe capaz de gerar outros gerentes, bom fzr isso
*/

public class Gerente extends Usuario implements Utilidades{
    // Para evitar que haja a criação de um Gerente que não é funcionario

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
    public void listarFuncionariosAtivos(int quant){
        if (quant <= 0){
            System.out.println("Quantidade para listar errada, tente novamente");
            return;
        }

        String listaFuncionarios = "SELECT nome, email FROM funcionarios WHERE ativo = 1 ORDER BY id LIMIT ?";

        try (Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(listaFuncionarios)){

            ps.setInt(1, quant);

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
    public void listarClientes(int quant){
        if (quant <= 0){
            System.out.println("Quantidade para listar errada, tente novamente");
            return;
        }

        String listaClientes = "SELECT nome, email FROM clientes ORDER BY id LIMIT ?";

        try (Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(listaClientes)){

            ps.setInt(1, quant);

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
    
    // demitir funcionario
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

    // contratar funcionario
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

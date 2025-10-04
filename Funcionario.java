import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* Classe Funcionario que "trata" dos pets

 *  Atributos:
 *  nome, email
 * 
 *  Métodos que são chamados (public):
 * 
 *  checaPetsEmEspera(limite), checa quantos pets estao precisando de banho/tosa, tambem informa o id deles na lista que é crucial para o funcionamento dos outros métodos
 *  tosar, usa do id da lista para "tosar", (altera o atributo para 1)
 *  darBanho, mesma coisa que tosar mas agora para banho
 *  
 *  toString, retorna nome e email do funcionario 
 *  listarMetodos, retorna uma descrição simples do que um funcionário pode fazer 
 * 
 *  Métodos que são chamados por tosar e darBanho:
 *      petPronto (se ele estiver nos conformes ele é retirado da lista de espera)
*       adicionaPedidoConcluido (armazena toda ação que foi feita, se x pet foi tosado ou banhado em que dia e por quem)
 */



public class Funcionario extends Usuario implements Utilidades{

    public Funcionario(String nome, String email){
        super(nome, email);
    }

    public void checaPetsEmEspera(int lim){
        // Essa query junta as informações compativeis do id de pets com o id exposto na lista de espera
        // e junta os dados de clientes com o id dos pets que estao na lista
        // CUIDADO COM OS NOMES
            String sql = """
            SELECT le.id AS entrada_id,
                c.nome AS nome_dono,
                p.id AS pet_id,
                p.nome AS nome_pet,
                p.raca AS raca_pet,
                p.banho,
                p.tosa,
                le.pedido_feito
            FROM lista_de_espera le
            JOIN pets p ON le.pet_id = p.id
            JOIN clientes c ON p.user_id = c.id
            ORDER BY le.pedido_feito
            LIMIT ?;
            """;

        try(Connection con = Database.getConnection();           
            PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, lim);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    int idEntrada = rs.getInt("entrada_id");
                    String nomeDono = rs.getString("nome_dono");
                    String nomePet = rs.getString("nome_pet");
                    String racaPet = rs.getString("raca_pet");
                    boolean banho = rs.getBoolean("banho");
                    boolean tosa = rs.getBoolean("tosa");
                
                    System.out.println(
                    "Id da entrada: " + idEntrada +
                    "\nDono: " + nomeDono +
                    "\nNome do pet: " + nomePet +
                    "\nRaca: " + racaPet + 
                    "\nEsta limpo: " + banho +
                    "\nEsta tosado: " + tosa +
                    "\nPedido recebido: " + rs.getDate("pedido_feito")
                    );
                }
            }
            

        } catch (SQLException e){
            System.err.println("Erro ao listar pets em espera: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Usa do id exposto na tabela para tornar o atributo tosa em true
    public void tosar(int idLista){
        try(Connection con = Database.getConnection();
            PreparedStatement psPet = con.prepareStatement("SELECT pet_id FROM lista_de_espera WHERE id = ?");
            PreparedStatement psTosa = con.prepareStatement("SELECT tosa FROM pets WHERE id = ?");
            PreparedStatement psUpdateTosa = con.prepareStatement("UPDATE pets SET tosa = 1 WHERE id = ?")){

            psPet.setInt(1, idLista);
            try (ResultSet rsPet = psPet.executeQuery()){
                if (rsPet.next()){
                    int petId = rsPet.getInt("pet_id");

                    psTosa.setInt(1, petId);    
                    try (ResultSet rsTosa = psTosa.executeQuery()){
                        if (rsTosa.next()){
                            if (rsTosa.getBoolean("tosa")){
                                System.out.println("Pet nao precisa de tosa.");
                                return;
                            }

                            psUpdateTosa.setInt(1, petId);
                            psUpdateTosa.executeUpdate();
                            System.out.println("Pet " + petId + " foi tosado!");
                            
                            petPronto(con, petId);
                            adicionaPedidoConcluido(con, petId, false, true);
                        }
                    }
                }
            }
        } catch (SQLException e){
            System.err.println("Erro ao tosar pet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Usa do id exposto na tabela para tornar o atributo banho true
    public void darBanho(int idLista){

        try(Connection con = Database.getConnection();
            PreparedStatement psPet = con.prepareStatement("SELECT pet_id FROM lista_de_espera WHERE id = ?");
            PreparedStatement psBanho = con.prepareStatement("SELECT banho FROM pets WHERE id = ?");
            PreparedStatement psUpdateBanho = con.prepareStatement("UPDATE pets SET banho = 1 WHERE id = ?")){

            psPet.setInt(1, idLista);
            try (ResultSet rsPet = psPet.executeQuery()){
                if (rsPet.next()){
                    int petId = rsPet.getInt("pet_id");
                    
                    psBanho.setInt(1, petId);
                    try (ResultSet rsBanho = psBanho.executeQuery()){
                        if (rsBanho.next()){
                            boolean banho = rsBanho.getBoolean("banho");

                            if (banho){
                                System.out.println("Pet nao precisa de banho.");
                                return;
                            }
                              
                            psUpdateBanho.setInt(1, petId);
                            psUpdateBanho.executeUpdate();
                            System.out.println("Pet " + petId + " tomou banho!");

                            petPronto(con, petId);
                            adicionaPedidoConcluido(con, petId, true, false);
                        }
                    }
                }
                else{
                    System.out.println("Entrada da lista nao encontrada, id: " + idLista);
                }
            }
        } catch (SQLException e){
            System.err.println("Erro ao dar banho no pet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Caso dê algum erro manda pra o outro metodo a exception
    private boolean petPronto(Connection con, int idPet) throws SQLException{
        try (PreparedStatement psLimpeza = con.prepareStatement("SELECT banho, tosa FROM pets WHERE id = ?");
            PreparedStatement psDeletaPet = con.prepareStatement("DELETE FROM lista_de_espera WHERE pet_id = ?")){  

            psLimpeza.setInt(1, idPet);
            try (ResultSet rsLimpo = psLimpeza.executeQuery()){
                if (rsLimpo.next()){

                    boolean banho = rsLimpo.getBoolean("banho");
                    boolean tosa = rsLimpo.getBoolean("tosa"); 

                    if (banho && tosa){
                        psDeletaPet.setInt(1, idPet);
                        psDeletaPet.executeUpdate();
                        System.out.println("Pet com id = "+ idPet + " foi removido da lista de espera.");

                        return true;
                    }
                }
                else{
                    System.out.println("Pet com id = " + idPet + " não encontrado.");
                }
                return false;
            }
        }
    }

    // Função que adiciona o funcionario, o pet e o serviço feito no pet na table pedido_concluidos
    private void adicionaPedidoConcluido(Connection con, int idPet, boolean banho, boolean tosa) throws SQLException{
        try (PreparedStatement psIdFunc = con.prepareStatement("SELECT id FROM funcionarios WHERE email = ?");
        PreparedStatement psInsert = con.prepareStatement("INSERT INTO pedidos_concluidos (func_id, pet_id, banho, tosa) VALUES (?,?,?,?)")){
        
        psIdFunc.setString(1, this.getEmail());
        try (ResultSet rsIdFunc = psIdFunc.executeQuery()){
            if (rsIdFunc.next()){

                psInsert.setInt(1, rsIdFunc.getInt("id"));
                psInsert.setInt(2, idPet);
                psInsert.setBoolean(3, banho);
                psInsert.setBoolean(4, tosa);

                psInsert.executeUpdate();
            }
        }
        } catch (SQLException e){
            throw e;
        }
    }

    @Override
    public String toString(){
        return "Nome do Funcionario: " + this.getNome() +
                "\nEmail do Funcionario: " + this.getEmail();
    }

    @Override
    public String listarMetodos(){
        return "O funcionario pode: Checar pets em espera, pets que precisam de tosa ou banho\n" +
                "Tosar e dar banho em um pet, dependendo de sua necessidade que pode ser vista pela funcao anterior.\n" +
                "Perceba que eh importante visualizar o id do pet na lista de espera para realizar esses metodos (tosar e dar banho).\n";
    }

}

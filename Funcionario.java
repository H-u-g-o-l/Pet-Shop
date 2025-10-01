import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Funcionario extends Usuarios{

    public Funcionario(String nome, String email){
        super(nome, email);
    }

    // Talvez seja legal retornar string, vou decidir
    // Printa os pets que estao no banco de dados que precisam de banho/tosa
    public void checaPetsEmEspera(){
        String url = "jdbc:sqlite:C:\\Users\\hgbr1\\Programas\\Exercises\\PetShop\\Pet-Shop\\petshop.db";

        try(Connection con = DriverManager.getConnection(url)){
            Statement stmt = con.createStatement();
            
            String listaQuery = "SELECT * FROM lista_de_espera";
            
            ResultSet rs = stmt.executeQuery(listaQuery);

            while (rs.next()){
                int id = rs.getInt("id");
                int petId = rs.getInt("pet_id");

                // Query que retorna o nome, raça, banho e tosa dos pets que precisam de serviço
                String petQuery = "SELECT user_id, nome, raca, banho, tosa FROM pets WHERE id = " + petId;
                ResultSet pet = stmt.executeQuery(petQuery);

                if (pet.next()){
                    int donoId = pet.getInt("user_id");
                    String nomePet = pet.getString("nome");
                    String racaPet = pet.getString("raca");
                    boolean limpo = pet.getBoolean("banho");
                    boolean tosado = pet.getBoolean("tosa");

                    // fazer uma query que pega o nome da pessoa responsavel pelo pet
                    // RESULT SET PRECISA DE UM TRY
                    ResultSet dono = stmt.executeQuery("SELECT nome FROM clientes WHERE id = " + donoId);
                    String donoNome = dono.getString("nome");

                    System.out.println(
                    "Id da entrada: " + id +
                    "\nDono: " + donoNome +
                    "\nNome do pet: " + nomePet +
                    "\nRaca: " + racaPet + 
                    "\nEsta limpo: " + limpo +
                    "\nEsta tosado: " + tosado +
                    "\nPedido recebido: " + rs.getDate("pedido_feito")
                    );
                }
                // MUDAR FORMA DE LIDAR COM O ELSE
                else{
                    System.out.println("Fim");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    // Usa do id exposto na tabela para tornar o atributo tosa em true
    public void tosar(int idLista){
        String url = "jdbc:sqlite:C:\\Users\\hgbr1\\Programas\\Exercises\\PetShop\\Pet-Shop\\petshop.db";

        try(Connection con = DriverManager.getConnection(url)){
            Statement stmt = con.createStatement();

            String queryIdPet = "SELECT pet_id FROM lista_de_espera WHERE id = " + idLista;
            ResultSet idPet = stmt.executeQuery(queryIdPet);
            
            // logica simples de checagem se o valor é true ou false
            String queryTosa = "SELECT tosa FROM pets WHERE id = " + idPet.getInt("pet_id");
            ResultSet tosa = stmt.executeQuery(queryTosa);

            if (tosa.getBoolean("tosa")){
                System.out.println("Pet nao precisa de tosa.");
                return;
            }
            else{
                // Query que da um update no valor de tosa, tornando entao true
                String queryTosaTrue = "UPDATE pets SET tosa = 1 WHERE id = " + idPet.getInt("pet_id");
                stmt.executeUpdate(queryTosaTrue);
            }

            petPronto(idPet.getInt("pet_id"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Usa do id exposto na tabela para tornar o atributo banho true
    public void darBanho(int idLista){
        String url = "jdbc:sqlite:C:\\Users\\hgbr1\\Programas\\Exercises\\PetShop\\Pet-Shop\\petshop.db";

        try(Connection con = DriverManager.getConnection(url)){
            Statement stmt = con.createStatement();

            String queryIdPet = "SELECT pet_id FROM lista_de_espera WHERE id = " + idLista;
            ResultSet idPet = stmt.executeQuery(queryIdPet);
            
            // logica simples de checagem se o valor é true ou false
            String queryBanho = "SELECT banho FROM pets WHERE id = " + idPet.getInt("pet_id");
            ResultSet banho = stmt.executeQuery(queryBanho);

            if (banho.getBoolean("banho")){
                System.out.println("Pet nao precisa de banho.");
                return;
            }
            else{
                // Query que da um update no valor de banho, tornando entao true
                String queryBanhoTrue = "UPDATE pets SET banho = 1 WHERE id = " + idPet;
                stmt.executeUpdate(queryBanhoTrue);
            }

            petPronto(idPet.getInt("pet_id"));
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean petPronto(int idPet){
        String url = "jdbc:sqlite:C:\\Users\\hgbr1\\Programas\\Exercises\\PetShop\\Pet-Shop\\petshop.db";

        // fazer uma query que checa se banho e tosa é true, e retira a instancia do pet da table de lista_de_espera
        
        try(Connection con = DriverManager.getConnection(url)){
            Statement stmt = con.createStatement();
            String checaLimpeza = "SELECT banho, tosa FROM pets WHERE id = " + idPet;

            ResultSet limpo = stmt.executeQuery(checaLimpeza);

            if (limpo.getBoolean("banho") && limpo.getBoolean("tosa")){
                // query pra retirar o pet da table de lista_de_espera
                String deletaPet = "DELETE FROM lista_de_espera WHERE pet_id = " + idPet;
                stmt.executeUpdate(deletaPet);

                return true;
            }   
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

}

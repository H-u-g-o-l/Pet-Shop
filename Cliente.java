import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// java -cp "C:\Users\hgbr1\Programas\Exercises\PetShop\Pet-Shop" MyMainClass

public class Cliente extends Usuarios{
    private ArrayList<Pet> pet;

    // Para evitar que haja a criação do objeto na memória foi usado do Factory Method
    private Cliente(String nome, String email, boolean persistido){
        super(nome, email);
        this.pet = new ArrayList<Pet>();
    }

    // Adiciona o cliente ao db caso o email seja unico
    public static Cliente criarEPersistir(String nome, String email) throws EmailJaCadastrado, SQLException{
        String url = "jdbc:sqlite:C:\\Users\\hgbr1\\Programas\\Exercises\\PetShop\\Pet-Shop\\petshop.db";
        String sql = "INSERT INTO clientes (nome, email) VALUES (?, ?)";

        try(Connection con = DriverManager.getConnection(url);
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, nome);
            ps.setString(2, email);
            ps.executeUpdate();

            return new Cliente(nome, email, true);
        } catch (SQLException e){
            String erro = e.getMessage();

            if (erro.contains("unique") || erro.contains("uniqueness")){
                throw new EmailJaCadastrado("Email ja cadastrado: " + email);
            }
            throw e;
        }
    }

    // Getters e Setters
    public ArrayList<Pet> getPet() {
        return this.pet;
    }

    public void adicionaPet(Pet pet) {
        String url = "jdbc:sqlite:C:\\Users\\hgbr1\\Programas\\Exercises\\PetShop\\Pet-Shop\\petshop.db";
        String inserir = "INSERT INTO pets (user_id, nome, raca, banho, tosa) VALUES (?, ?, ?, ?, ?)";
        String buscaIdDono = "SELECT id FROM clientes WHERE email = ?";

        try (Connection con = DriverManager.getConnection(url);
            PreparedStatement psUpdate = con.prepareStatement(inserir);
            PreparedStatement psIdDono = con.prepareStatement(buscaIdDono)){
            
            psIdDono.setString(1, this.getEmail());
            try (ResultSet rsId = psIdDono.executeQuery()){
                if (rsId.next()){
                    int donoId = rsId.getInt("id");

                    psUpdate.setInt(1, donoId);
                    psUpdate.setString(2, pet.getNomePet());
                    psUpdate.setString(3, pet.getRaca());
                    psUpdate.setBoolean(4, true);
                    psUpdate.setBoolean(5, true);

                    psUpdate.executeUpdate();
                    this.pet.add(pet);

                    System.out.println("Pet adicionado com sucesso!");
                }
            }
        } catch(Exception e){
            System.err.println("Erro ao adicionar pet");
            e.printStackTrace();
        }
    }


    // Alterar a parte dos pets dps
    @Override
    public String toString(){
        return super.toString() +
            "\nPet(s): " + this.pet;
    }
}

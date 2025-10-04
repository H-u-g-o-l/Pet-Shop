package usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import main.Database;
import main.Utilidades;
import pets.Cachorro;
import pets.Gato;
import pets.Pet;

/* Classe Cliente possui: 
 * 
 * -Nome
 * -Email unico
 * 
 * 
 * Metodos:
 * 
 * contrutor publico (factory)
 * criarEPersistir (adiciona o objeto na memoria se e somente se ele ir para o banco de dados)
 * 
 * Metodo pra retornar os pets com seus nomes:
 * adicionaPet (add em memoria e no banco de dados)
 * 
 * marcaBanho (vai por o pet "nome" para a lista_de_espera)
 * marcaTosa (poe o pet para a lista de espera)
 * 
 * toString, retorna nome, email e pets caso haja algum
 * 
 * listarMetodos, retorna uma simples descrição do que um cliente pode fazer
 */

public class Cliente extends Usuario implements Utilidades{

    public Cliente(){
        super(null, null);
    }

    // Para evitar que haja a criação do objeto na memória foi usado do Factory Method
    private Cliente(String nome, String email, boolean persistido){
        super(nome, email);
    }

    // Adiciona o cliente ao db caso o email seja unico
    public static Cliente criarEPersistir(String nome, String email){

        if (Usuario.checaNome(nome) == null || Usuario.checaEmail(email) == null){
            return null;
        }

        String inserirCliente = "INSERT INTO clientes (nome, email) VALUES (?, ?)";

        try(Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(inserirCliente)){

            ps.setString(1, nome);
            ps.setString(2, email);
            ps.executeUpdate();

            return new Cliente(nome, email, true);
        } catch (SQLException e){
            String erro = e.getMessage();

            if (erro.contains("unique") || erro.contains("uniqueness")){
                System.out.println("Email ja cadastrado: " + email);
            }
            else{
                System.err.println("Erro ao criar cliente: " + erro);
                e.printStackTrace();
            }
            return null;
        }
    }

    public void adicionarPet(String nome, String especie, String raca){
        Scanner sc = new Scanner(System.in);
        
        int escolha = -1;
        System.out.println("Por favor digite: 1 se seu pet for um cachorro\n2 se seu pet for um gato \n3 se seu pet não for nenhum dos 2");
        while(true){
            try {
                escolha = sc.nextInt();
                break;
            } catch (InputMismatchException e){
                System.out.println("Resposta invalida. Tente Novamente.");
                sc.nextLine();
            }
        }

        switch(escolha){
            case 1:
                adicionarPet(new Cachorro(nome, "Cachorro", raca));
                break;
            case 2:
                adicionarPet(new Gato(nome, "Gato", raca));
                break;
            default:
                adicionarPet(new Pet(nome, especie, raca));
                break;
        }
    }

    public void adicionarPet(Pet pet) {
        String inserir = "INSERT INTO pets (user_id, nome, raca, banho, tosa) VALUES (?, ?, ?, ?, ?)";
        String buscaIdDono = "SELECT id FROM clientes WHERE email = ?";

        try (Connection con = Database.getConnection();
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

                    System.out.println("Pet adicionado com sucesso!");
                }
            }
        } catch(Exception e){
            System.err.println("Erro ao adicionar pet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void marcaBanho(String nomePet){
        String buscaCliente = "SELECT id FROM clientes WHERE email = ?";
        String buscaPet = "SELECT id FROM pets WHERE user_id = ? AND nome = ?";
        String updateBanho = "UPDATE pets SET banho = 0 WHERE id = ?";
        String insertLista = "INSERT INTO lista_de_espera (pet_id) VALUES (?)";

        try (Connection con = Database.getConnection();
            PreparedStatement psCliente = con.prepareStatement(buscaCliente);
            PreparedStatement psPet = con.prepareStatement(buscaPet);
            PreparedStatement psUpdate = con.prepareStatement(updateBanho);
            PreparedStatement psInsert = con.prepareStatement(insertLista)){

            psCliente.setString(1, this.getEmail());
            try (ResultSet rsCliente = psCliente.executeQuery()){
                if (rsCliente.next()){
                    int clienteId = rsCliente.getInt("id");

                    psPet.setInt(1, clienteId);
                    psPet.setString(2, nomePet);
                    try (ResultSet rsPet = psPet.executeQuery()){
                        if (rsPet.next()){
                            int petId = rsPet.getInt("id");

                            psUpdate.setInt(1, petId);
                            psUpdate.executeUpdate();
                            
                            psInsert.setInt(1, petId);
                            psInsert.executeUpdate();

                            System.out.println("Banho marcado para o pet " + nomePet);
                        }
                        else{
                            System.out.println("Nao foi encontrado pet associado ao cliente. Tente 'adicionarPet' primeiro");
                        }
                    }
                }
                else{
                    System.out.println("Cliente nao encontrado");
                }
            }    
        } catch (SQLException e){
            System.err.println("Erro ao marcar o banho: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void marcaTosa(String nomePet){
        String buscaCliente = "SELECT id FROM clientes WHERE email = ?";
        String buscaPet = "SELECT id FROM pets WHERE user_id = ? AND nome = ?";
        String updateTosa = "UPDATE pets SET tosa = 0 WHERE id = ?";
        String insertLista = "INSERT INTO lista_de_espera (pet_id) VALUES (?)";

        try (Connection con = Database.getConnection();
            PreparedStatement psCliente = con.prepareStatement(buscaCliente);
            PreparedStatement psPet = con.prepareStatement(buscaPet);
            PreparedStatement psUpdate = con.prepareStatement(updateTosa);
            PreparedStatement psInsert = con.prepareStatement(insertLista)){

            psCliente.setString(1, this.getEmail());
            try (ResultSet rsCliente = psCliente.executeQuery()){
                if (rsCliente.next()){
                    int clienteId = rsCliente.getInt("id");

                    psPet.setInt(1, clienteId);
                    psPet.setString(2, nomePet);
                    try (ResultSet rsPet = psPet.executeQuery()){
                        if (rsPet.next()){
                            int petId = rsPet.getInt("id");

                            psUpdate.setInt(1, petId);
                            psUpdate.executeUpdate();
                            
                            psInsert.setInt(1, petId);
                            psInsert.executeUpdate();

                            System.out.println("Tosa marcado para o pet " + nomePet);
                        }
                        else{
                            System.out.println("Nao foi encontrado pet associado ao cliente. Tente 'adicionarPet' primeiro");
                        }
                    }
                }
                else{
                    System.out.println("Cliente nao encontrado");
                }
            }    
        } catch (SQLException e){
            System.err.println("Erro ao marcar a tosa: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        // Usada pra poder concatenar, String nao deixa
        StringBuilder sb = new StringBuilder();
        
        //                                          nome e email             \n
        sb.append("Nome do Cliente: ").append(this.getNome()).append(System.lineSeparator());
        sb.append("Email do Cliente: ").append(this.getEmail()).append(System.lineSeparator());

        // JOIN pra evitar duas queries separadas
        //          Selecione nome de pets onde o id em pet "user_id" == "id" <- cliente e email = this.getEmail
        String buscaNomePet = "SELECT p.nome FROM pets p JOIN clientes c ON p.user_id = c.id WHERE c.email = ?";

        try (Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(buscaNomePet)) {

            ps.setString(1, this.getEmail());
            try (ResultSet rs = ps.executeQuery()){

                // flag pra saber se tem ou nao tem pet
                boolean hasAny = false;
                sb.append("Pets: ");
                while (rs.next()) {
                    if (hasAny) sb.append(", ");
                    sb.append(rs.getString("nome"));
                    hasAny = true;
                }
                if (!hasAny) {
                    sb.append("nenhum");
                }

                sb.append(System.lineSeparator());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar pets: " + e.getMessage());
            e.printStackTrace();
            sb.append("Pets: (erro ao buscar)").append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String listarMetodos(){
        return "O cliente pode: adicionar um pet e marcar banho ou tosa, use dos nomes de seu pet para realizar o (s) metodo (s)";
    }
}

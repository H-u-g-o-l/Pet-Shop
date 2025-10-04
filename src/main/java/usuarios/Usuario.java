package usuarios;
import java.util.regex.Pattern;

/* Classe pai de gerente, funcionario e cliente.
 * 
 * Possui:
 * Nome e email
 * 
 * Métodos:
 * 
 *  getNome, getEmail
 * 
 *  setNome, certifica que o nome é válido e normaliza ele para facilitar o armazenamento
 *  setEmail, certifica que a String inserida é um email e normaliza ele também
 *  
 *  checaNome e checaEmail são chamados pelos setters
 */


public class Usuario{
    private String nome;
    private String email;

    // Padrao pra checar nome e email com regex
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zÀ-ÿ'\\-\\s]{2,}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public Usuario(String nome, String email){
        setEmail(email);
        setNome(nome);
    }

    public String getNome() {
        return this.nome;
    }

    // Checa se o nome é um nome mesmo e normaliza ele
    public void setNome(String nome){
        this.nome = checaNome(nome);
    }
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email){
        this.email = checaEmail(email);
    }


    public static String checaEmail(String email){
        String emailAtualizado = email.trim();

        if (!EMAIL_PATTERN.matcher(email).matches()){
            return null;
        }

        return emailAtualizado.toLowerCase();
    }

    public static String checaNome(String nome){
        String nomeAtualizado = nome.trim();

        if (!NAME_PATTERN.matcher(nomeAtualizado).matches()){
            return null;
        }

        return nomeAtualizado.toLowerCase();
    }
}

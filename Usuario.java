import java.util.regex.Pattern;

public class Usuario{
    private String nome;
    private String email;

    // Padrao pra checar nome e email com regex
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zÀ-ÿ'\\-\\s]{2,}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public Usuario(String nome, String email) throws IllegalArgumentException{
        setEmail(email);
        setNome(nome);
    }

    public String getNome() {
        return this.nome;
    }

    // Checa se o nome é um nome mesmo e normaliza ele
    public void setNome(String nome) throws IllegalArgumentException{
        if (nome == null){
            throw new IllegalArgumentException("Nome invalido: " + nome);
        }

        // Retira os espaços
        String n = nome.trim();

        if (!NAME_PATTERN.matcher(n).matches()){
            throw new IllegalArgumentException("Nome invalido: " + nome);
        }

        this.nome = n.replaceAll("\\s{2,}", " ");
    }
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) throws IllegalArgumentException{
        if (email == null){
            throw new IllegalArgumentException("Nome invalido: " + nome);
        }

        String e = email.trim();

        if (!EMAIL_PATTERN.matcher(e).matches()){
            throw new IllegalArgumentException("Nome invalido: " + nome);
        }

        this.email = e.toLowerCase();
    }
}

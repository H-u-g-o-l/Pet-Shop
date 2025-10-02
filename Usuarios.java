public abstract class Usuarios {
    private String nome;
    private String email;

    public Usuarios(String nome, String email){
        setEmail(email);
        setNome(nome);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        // add regex pra certificar que é um nome
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        // checar se é um email válido
        this.email = email;
    }

    @Override
    public abstract String toString();
}

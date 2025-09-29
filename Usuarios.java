public abstract class Usuarios {
    private int id;
    private String nome;
    private String email;

    public Usuarios(int id, String nome, String email){
        this.id = id;        
        setEmail(email);
        setNome(nome);
    }


    public int getId() {
        return this.id;
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

    public String toString(){
        return "Id: " + this.id +
                "Nome: " + this.nome +
                "Email: " + this.email;
    }
}

public class Pet {
    private String nomePet;
    private String especie;
    private boolean tosar;
    private boolean banho;

    public Pet(String nomePet,String especie, boolean banho, boolean tosar) {
        this.nomePet = nomePet;
        this.especie = especie;
        this.tosar = tosar;
        this.banho = banho;
    }

    public String getNomePet() {
        return this.nomePet;
    }

    public String getEspecie() {
        return this.especie;
    }
    
    // Checar a necessidade de um getter/setter pra tosar e banho

    // ALTERAR
    public String toString(){
        return "\nNomePet: " + this.nomePet +
            "\nEspecie: " + this.especie;
    }
}

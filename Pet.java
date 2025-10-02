public class Pet {
    private String nomePet;
    private String raca;

    public Pet(String nomePet,String raca) {
        this.nomePet = nomePet;
        this.raca = raca;
    }

    public String getNomePet() {
        return this.nomePet;
    }

    public String getRaca() {
        return this.raca;
    }
    
    // Checar a necessidade de um getter/setter pra tosar e banho

    // ALTERAR
    public String toString(){
        return "\nNomePet: " + this.nomePet +
            "\nRaca: " + this.raca;
    }
}

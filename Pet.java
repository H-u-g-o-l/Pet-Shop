public class Pet {
    private String nomePet;
    private String raca;
    private boolean tosar;
    private boolean banho;

    public Pet(String nomePet,String raca, boolean banho, boolean tosar) {
        this.nomePet = nomePet;
        this.raca = raca;
        this.tosar = tosar;
        this.banho = banho;
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

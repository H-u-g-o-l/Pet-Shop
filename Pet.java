public class Pet {
    int idPet;
    String nomePet;
    String raca;
    String especie;

    public Pet(int idPet, String nomePet, String raca, String especie) {
        this.idPet = idPet;
        this.nomePet = nomePet;
        this.raca = raca;
        this.especie = especie;
    }

    public int getIdPet() {
        return this.idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public String getNomePet() {
        return this.nomePet;
    }

    public void setNomePet(String nomePet) {
        this.nomePet = nomePet;
    }

    public String getRaca() {
        return this.raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getEspecie() {
        return this.especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
    
    public String toString(){
        return "PetId: " + this.idPet + 
            "\nNomePet: " + this.nomePet +
            "\nRaca: " + this.raca +
            "\nEspecie: " + this.especie;
    }
}

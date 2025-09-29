public class Pet {
    private int idPet;
    private String nomePet;
    private String especie;
    private boolean tosar;

    // No momento o id pode ser repetido, uma implementação com hash table pode evitar isso ou o uso de um banco de dados.
    public Pet(int idPet, String nomePet,String especie, boolean tosar) {
        this.idPet = idPet;
        this.nomePet = nomePet;
        this.especie = especie;
        this.tosar = tosar;
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

    public String getEspecie() {
        return this.especie;
    }
    
    public boolean getTosar(){
        return this.tosar;
    }

    public String toString(){
        return "PetId: " + this.idPet + 
            "\nNomePet: " + this.nomePet +
            "\nEspecie: " + this.especie;
    }
}

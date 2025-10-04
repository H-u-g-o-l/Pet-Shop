public class Pet {
    private String nomePet;
    private String especie;
    private String raca;

    public Pet(String nomePet,String especie) {
        this.nomePet = nomePet.toLowerCase();
        this.especie = especie.toLowerCase();
        this.raca = "srd";
    }
    public Pet (String nomePet, String especie, String raca){
        this.nomePet = nomePet.toLowerCase();
        this.especie = especie.toLowerCase();
        this.raca = raca.toLowerCase();
    }

    public String getNomePet() {
        return this.nomePet;
    }

    public String getEspecie() {
        return this.especie;
    }
    
    public String getRaca(){
        return this.raca;
    }
}

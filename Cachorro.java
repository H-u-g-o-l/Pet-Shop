public class Cachorro extends Pet{
    private String raca;

    public Cachorro(int idPet, String nomePet,String especie, boolean tosar, String raca){
        super(idPet, nomePet, especie, true);
        this.raca = raca;
    }

    @Override
    public String toString(){
        return super.toString() + "\nRaca: " + this.raca;
    }
}

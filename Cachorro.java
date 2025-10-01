public class Cachorro extends Pet{
    private String raca;

    public Cachorro(String nomePet,String especie, boolean tosar, String raca){
        super(nomePet, especie, true, true);
        this.raca = raca;
    }

    @Override
    public String toString(){
        return super.toString() + "\nRaca: " + this.raca;
    }
}

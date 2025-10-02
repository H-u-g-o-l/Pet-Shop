public class Cachorro extends Pet{
    private String raca;

    public Cachorro(String nomePet,String especie, String raca){
        super(nomePet, especie);
        this.raca = raca;
    }

    @Override
    public String toString(){
        return super.toString() + "\nRaca: " + this.raca;
    }
}

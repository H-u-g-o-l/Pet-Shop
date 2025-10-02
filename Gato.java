public class Gato extends Pet{
    private String raca;

    public Gato(String nomePet,String especie, boolean tosar, String raca){
        super(nomePet, especie);
        this.raca = raca;
    }

    @Override
    public String toString(){
        return super.toString() + "\nRaca: " + this.raca;
    }
}

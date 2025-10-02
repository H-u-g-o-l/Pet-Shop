public class Gato extends Pet{

    public Gato(String nomePet,String especie, String raca){
        super(nomePet, especie, raca);
    }

    @Override
    public String toString(){
        return "Nome do gato: " + this.getNomePet() +
                "\nEspecie: " + this.getEspecie() +
                "\n Raca: " + this.getRaca();
    }
}

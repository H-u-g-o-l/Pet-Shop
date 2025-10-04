package src.main.java.pets;
public class Cachorro extends Pet{

    public Cachorro(String nomePet,String especie, String raca){
        super(nomePet, especie, raca);
    }

    @Override
    public String toString(){
        return "Nome do cachorro: " + this.getNomePet() +
                "\nEspecie: " + this.getEspecie() +
                "\n Raca: " + this.getRaca();
    }
}

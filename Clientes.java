import java.util.ArrayList;

// java -cp "C:\Users\hgbr1\Programas\Exercises\PetShop\Pet-Shop" MyMainClass

public class Clientes extends Usuarios{
    private ArrayList<Pet> pet;
    

    public Clientes(String nome, String email) {
        super(nome, email);
        this.pet = new ArrayList<Pet>();
    }

    // Getters e Setters
    public ArrayList<Pet> getPet() {
        return this.pet;
    }

    public void setPet(Pet pet) {
        this.pet.add(pet);
    }


    // Alterar a parte dos pets dps
    @Override
    public String toString(){
        return super.toString() +
            "\nPet(s): " + this.pet;
    }
}

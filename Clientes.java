import java.util.ArrayList;

public class Clientes extends Usuarios{
    private ArrayList<Pet> pet;
    private double saldo;   // Opcional, talvez descarte depois  
    
    // Construtor sem saldo
    public Clientes(int id, String nome, String email, ArrayList<Pet> pet) {
        super(id, nome, email);
        this.pet = pet;
        this.saldo = 0;
    }

    // Construtor com tudo
    public Clientes(int id, String nome,String email, ArrayList<Pet> pet, double saldo) {
        super(id, nome, email);
        this.pet = pet;
        this.saldo = saldo;
    }

    // Getters e Setters

    public ArrayList<Pet> getPet() {
        return this.pet;
    }

    public void setPet(Pet pet) {
        this.pet.add(pet);
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Alterar a parte dos pets dps
    @Override
    public String toString(){
        return super.toString() +
            "\nSaldo: " + this.saldo +
            "\nPet(s): " + this.pet;
    }
}

import java.util.ArrayList;

public class Clientes {
    private int id;
    private String nome;
    private ArrayList<Pet> pet;
    private double saldo;   // Opcional, talvez descarte depois  
    
    // Construtor sem saldo
    public Clientes(int id, String nome, ArrayList<Pet> pet) {
        this.id = id;
        this.nome = nome;
        this.pet = pet;
        this.saldo = 0;
    }

    // Construtor com tudo
    public Clientes(int id, String nome, ArrayList<Pet> pet, double saldo) {
        this.id = id;
        this.nome = nome;
        this.pet = pet;
        this.saldo = saldo;
    }

    // Getters e Setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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
    public String toString(){
        return "Id cliente: " + this.id +
            "\nNome: " + this.nome +
            "\nSaldo: " + this.saldo +
            "\nPet(s): " + this.pet;
    }
}

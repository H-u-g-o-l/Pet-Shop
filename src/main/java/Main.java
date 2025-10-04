import usuarios.Login;
import usuarios.Usuario;
import pets.Pet;

public class Main {
    public static void main(String[] args) {
        Login login = new Login();
        Usuario hugo = login.logar("Hugo", "emailpratrabalho@gmail.com");
    }
}



package src.main.java;

import src.main.java.usuarios.Login;
import src.main.java.usuarios.Usuario;

public class Main{
    public static void main(String[] args) {
        Login login = new Login();

        Usuario hugo = login.logar("Hugo", "emailpratrabalho@gmail.com");
    }
}


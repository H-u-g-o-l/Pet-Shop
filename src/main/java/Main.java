import usuarios.*;
import pets.Pet;

public class Main {
    public static void main(String[] args) {
         Login login = new Login();
         Sistema sistema = new Sistema();


         int entrada = 0;

         while(entrada != 3){
             entrada = sistema.inicio();

             // Logando -> Pode ser: 1 - cli, 2 - func, 3 - gere
             if (entrada == 1){
                int res = sistema.promptLogin();

                switch (res){
                    case 1:
                        Cliente cliente = login.logarCliente();
                        if (cliente != null) sistema.opcoesCliente(cliente);
                        break;

                    case 2:
                        Funcionario funcionario = login.logarFuncionario();
                        if (funcionario != null) sistema.opcoesFuncionario(funcionario);
                        break;

                    case 3:
                        Gerente gerente = login.logarGerente();
                        if (gerente != null) sistema.opcoesGerente(gerente);
                }
             }

             // Registrando novo cliente
             else if (entrada == 2){
                login.registrar();
             }

             else{
                 System.out.println("\nObrigado por usar o sistema, ate mais!");
             }
         }
    }
}


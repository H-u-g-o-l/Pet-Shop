public class Funcionario extends Usuarios{
    // funcionario tem horario pra trabalhar 
    // pra facilitar de começo tem apenas a flag "trabalhando"
    // seria muito foda adicionar um meio de tornar ela true ou false ao depender do horario atual
    public boolean trabalhando = false;
    // private String horarioDeTrabalho


    public Funcionario(int id, String nome, String email){
        super(id, nome, email);
    }

    // Add metodos Limpar, tosar e etc..
    // add get e set pra mudar ou ver os horarios de trabalho
}

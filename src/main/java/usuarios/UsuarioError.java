package usuarios;

public class UsuarioError extends Exception {
    public int kind;

    public UsuarioError(int kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        String msg = "";
        switch (kind) {
            case 1:
                msg = "Email inválido";
                break;
            case 2:
                msg = "Email já cadastrado! Tentou outro";
        }

        return msg;
    }
}

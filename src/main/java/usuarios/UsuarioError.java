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
                msg = "\nEmail invalido";
                break;
            case 2:
                msg = "\nEmail ja cadastrado! Tente outro";
        }

        return msg;
    }
}

package usuarios;

public class UsuarioError extends Exception {
    public int kind;

    public UsuarioError(int kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return switch (kind) {
            case 1 -> "Email inválido";
            case 2 -> "Email já cadastrado! Tentou outro";
            default -> "";
        };
    }
}

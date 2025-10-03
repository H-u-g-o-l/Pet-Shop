// Classe criada com o intuito de encapsular url e centralizar noções do banco de dados
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String url = "jdbc:sqlite:C:\\Users\\hgbr1\\Programas\\Exercises\\PetShop\\Pet-Shop\\petshop.db";

    // Construtor que nao inicia nada, clean
    private Database (){};

    public static Connection getConnection() throws SQLException{
        Connection con = DriverManager.getConnection(url);

        return con;
    }

    public static String getUrl(){
        return url;
    }
}

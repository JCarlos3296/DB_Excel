package excel_to_db;

/**
 *
 * @author josec
 */
//imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn_Database {
    
    private Connection connect = null;
    
    public void connDataBase() throws Exception {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/db_prueba?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user=root&password=rootadmin");
            System.out.println("¡Conection Successfull!");  
            
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    public boolean ejecutar(String sentencia) throws SQLException {
        return connect.createStatement().execute(sentencia);
    }

    public void close() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}  


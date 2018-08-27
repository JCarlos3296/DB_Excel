package adminpelis;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Jose
 */
public class DB_Pelis {
    private static Connection cnx = null;
    private PreparedStatement psInsertar = null;
    private Statement s;
    private ResultSet rs;
    private int codigo_genero;
    
    private static Connection obtener(){
        if(cnx == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                try {
                    cnx = DriverManager.getConnection("jdbc:mysql://localhost/db_prueba", "root", "rootadmin");
                } catch (SQLException ex) {
                    Logger.getLogger(DB_Pelis.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DB_Pelis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cnx;
    }   
    
    public static void cerrar(){
        if(cnx != null){
            try {
                cnx.close();
            } catch (SQLException ex) {
                Logger.getLogger(DB_Pelis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }
    
    //servicios para la Base de Datos
    public void llenarTablaPelicula(int id, String nombre, int annio){
        try {
            psInsertar = obtener().prepareStatement("INSERT INTO pelicula VALUES(?,?,?)");
            psInsertar.setInt(1, id);
            psInsertar.setString(2, nombre);
            psInsertar.setInt(3, annio);
            psInsertar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB_Pelis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void llenarTablaCategoria(String category){
        try {
            psInsertar = obtener().prepareStatement("INSERT INTO genero VALUES(null, ?)");
            psInsertar.setString(1, category);
            psInsertar.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } 
    }
    
    public void llenarTablaPeli_Cat(int id_pelicula, int id_categoria ){
        try {
            psInsertar = obtener().prepareStatement("INSERT INTO pelicula_genero VALUES(?,?)");
            psInsertar.setInt(1,id_pelicula);
            psInsertar.setInt(2, id_categoria);
            psInsertar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB_Pelis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int obtener_IdCategori(String categoria){
        try {
            s = obtener().createStatement();
            rs = s.executeQuery("SELECT codigo_categoria FROM genero WHERE descripcion = \""+ categoria +"\"");
            while(rs.next()){
                codigo_genero = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB_Pelis.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return codigo_genero;
    }    
}

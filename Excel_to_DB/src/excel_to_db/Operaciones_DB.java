package excel_to_db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 *
 * @author Jose
 */
public class Operaciones_DB {
    
    Conn_Database con = new Conn_Database();
    ArrayList<String> arrComplete = new ArrayList<>();
    
    //Atributos necesarios
    private HSSFRow row;
    private HSSFCell cell;
 
    private String strCell;
    private String [] split_Sentence;
    private String [] split_Letters;
    private String [] split_Category;
    private String year;
    
    public String addMovie(HSSFSheet sheet){
        
        try {
            con.connDataBase();
            System.out.println(sheet.getLastRowNum());
            
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                strCell = "";
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    strCell += cell.toString();
                }
                System.out.println(strCell);
                
                split_Sentence = strCell.split("::");
                split_Category = split_Sentence[2].split("\\|");
                for (int k = 0; k < split_Category.length; k++) {
                   if(arrComplete.isEmpty()){
                       arrComplete.add(split_Category[k]);
                       //System.out.println(catTemporal[j]);
                   }else{
                       if(!(arrComplete.contains(split_Category[k]))){
                           arrComplete.add(split_Category[k]);
                           //System.out.println("");
                       }
                    }
                }
                
                split_Letters = split_Sentence[1].split("\\(");
                
                if (split_Letters.length == 3) {
                    split_Letters[0] += ("(" + split_Letters[1]);
                    year = split_Letters[2].split("\\)")[0];
                } else if (split_Letters.length > 3 ) {
                    split_Letters[0] += ("(" + split_Letters[1] + "(" + split_Letters[2]);
                    year = split_Letters[3].split("\\)")[0];
                } else {
                    year = split_Letters[1].split("\\)")[0];
                }
                
                con.ejecutar("INSERT INTO pelicula (codigo_pelicula, descripcion, anio) values (" + split_Sentence[0]  + ",\"" + split_Letters[0]  + "\"," + year + ")");
                System.out.println("id: " + split_Sentence[0] + " nombre: " + split_Letters[0] + " año: " + year);
                
                
            }
            
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(Operaciones_DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Datos almacenados y recorridos con éxito!";
    }
    
    public String addCategory() throws SQLException, Exception{
        con.connDataBase();
        for (String n : arrComplete) {
                System.out.println("Ingresando categoria: " + n);
                con.ejecutar("INSERT INTO genero (descripcion) values(\"" + n + "\")");
            }
         con.close();
        return "Datos almacenados y recorridos con éxito!";
    }
    
    public String addMovUCat(HSSFSheet sheet) throws Exception{
        con.connDataBase();
        
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
           row = sheet.getRow(i);
           strCell = "";
           for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    strCell += cell.toString();
                }
           
           split_Sentence = strCell.split("::");
           split_Category = split_Sentence[2].split("\\|");
            for(String n : split_Category){
                if (con.ejecutar("SELECT codigo_categoria FROM `genero` WHERE descripcion = \"" + n + "\"")) {
                    con.ejecutar("INSERT INTO pelicula_genero(codigo_pelicula, codigo_categoria) VALUES ("+ split_Sentence[0]+","+ con.peticion(n) +")");
                }
            }
        }
        
        con.close();

        return "Datos almacenados y recorridos con éxito!";
    }
    
}

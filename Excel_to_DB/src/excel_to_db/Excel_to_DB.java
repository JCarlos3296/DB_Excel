package excel_to_db;

//imports
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author josec
 */

public class Excel_to_DB {
    public static void main(String[] args){
        try {
            Operaciones_DB action = new Operaciones_DB();
            File carpeta = new File("C:\\Users\\Jose\\Documents\\Softwares\\Git-Projects\\DB_Excel\\Excel_to_DB\\movies.xls");
            
            InputStream myFile = new FileInputStream(carpeta);
            HSSFWorkbook wb = new HSSFWorkbook(myFile);
            HSSFSheet sheet = wb.getSheet("movies");
            
            System.out.println("Espere, estamos almacenando los datos...");
            
            action.addMovie(sheet);
            System.out.println(action.addCategory());
            System.out.println(action.addMovUCat(sheet));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

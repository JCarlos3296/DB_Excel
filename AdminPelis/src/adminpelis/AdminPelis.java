package adminpelis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

/**
 *
 * @author Jose
 */
public class AdminPelis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            //Directorio del Archivo
            File file = new File("C:\\Users\\Jose\\Documents\\Softwares\\Git-Projects\\DB_Excel\\Excel_to_DB\\movies.xls");
            InputStream myFile = new FileInputStream(file);
            HSSFWorkbook wb = new HSSFWorkbook(myFile);
            
            //Elegir la hoja
            HSSFSheet sheet = wb.getSheet("movies");
            
            EjecutarPeli misPelis = new EjecutarPeli();
            
            misPelis.excelContent(sheet);
            System.out.println("");
            misPelis.seePeli(0);
            
        } catch (IOException ex) {
            Logger.getLogger(AdminPelis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

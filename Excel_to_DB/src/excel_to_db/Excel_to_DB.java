package excel_to_db;

//imports
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author josec
 */

public class Excel_to_DB {
    public static void main(String[] args){
        
        try {
            Conn_Database con = new Conn_Database();
            con.connDataBase();
            
            File carpeta = new File("C:\\Users\\josec\\Desktop\\DB_Excel\\Excel_to_DB\\movies.xls");
            
            InputStream myFile = new FileInputStream(carpeta);
            HSSFWorkbook wb = new HSSFWorkbook(myFile);
            HSSFSheet sheet = wb.getSheet("movies");

            HSSFCell cell;
            HSSFRow row;

            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                String strCell = "";
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    strCell += cell.toString();
                }
                String[] partirConjunto = strCell.split("::");
                String[] separarCadena = partirConjunto[1].split("\\(");
                String year;
                if(separarCadena.length > 2){
                   separarCadena[0] += ("(" + separarCadena[1]);
                   year = separarCadena[2].split("\\)")[0];
                } else {
                   year = separarCadena[1].split("\\)")[0];
                }
                con.ejecutar("INSERT INTO movies_tbl (_id, _name, _year, _category) values ('" + partirConjunto[0] + "', \"" + separarCadena[0]  + "\", \"" + year + "\", \"" + partirConjunto[2] +  "\")");
                System.out.println("{ id: " + partirConjunto[0] + " nombre: " + separarCadena[0] + " año: " + year + " categoria: " + partirConjunto[2] + " }");
            }
            con.close();
            System.out.println("");
            System.out.println("¡ " + sheet.getLastRowNum() +" Datos Impresos Correctamente!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

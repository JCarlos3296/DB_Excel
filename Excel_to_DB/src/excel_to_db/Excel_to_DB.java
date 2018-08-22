package excel_to_db;

//imports
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
            
            ArrayList<String> arrComplete = new ArrayList<String>();
                
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                String strCell = "";
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    strCell += cell.toString();
                }
                String[] partirConjunto = strCell.split("::");
                String[] separarCadena = partirConjunto[1].split("\\(");
                String[] catTemporal = partirConjunto[2].split("\\|");
                  
                String year;
                if(separarCadena.length == 3){
                   separarCadena[0] += ("(" + separarCadena[1]);
                   year = separarCadena[2].split("\\)")[0];
                }else if(separarCadena.length > 3){
                    separarCadena[0] += ("(" + separarCadena[1] + "(" + separarCadena[2] );
                    year = separarCadena[3].split("\\)")[0];
                }else {
                   year = separarCadena[1].split("\\)")[0];
                }
               
                for (int j = 0; j < catTemporal.length; j++) {
                   if(arrComplete.isEmpty()){
                       arrComplete.add(catTemporal[j]);
                       //System.out.println(catTemporal[j]);
                   }else{
                       if(!(arrComplete.contains(catTemporal[j]))){
                           arrComplete.add(catTemporal[j]);
                           //System.out.println("");
                       }
                    }
                }
                
                //, \"" + partirConjunto[2] +  "\"
                // + " categoria: " + partirConjunto[2]
             con.ejecutar("INSERT INTO movies_tbl (_name, _year) values (\"" + separarCadena[0]  + "\"," + year + ")");
                for (int j = 0; j < arrComplete.size(); j++) {
                    for (int k = 0; k < catTemporal.length; k++) {
                        if(arrComplete.get(j).contains(catTemporal[k])){
                            con.ejecutar("INSERT INTO mov_cat (id_movie, id_category) values("+ partirConjunto[0] +", "+ (j+1) +")");
                        }
                    }
                }
             System.out.println("{ id: " + partirConjunto[0] + " nombre: " + separarCadena[0] + " año: " + year + " }");
            }
            
            for (String n : arrComplete) {
                con.ejecutar("INSERT INTO categories_tbl (descripcion) values(\"" + n + "\")");
            }
            
            
            con.close();
            System.out.println("");
            System.out.println("¡ " + sheet.getLastRowNum() +" Datos Impresos Correctamente!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

package excel_to_db;

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

//Imports

public class Excel_to_DB {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        try {
            InputStream myFile = new FileInputStream(new File("C:\\Users\\josec\\Desktop\\DB_Excel\\movies.xls"));
            HSSFWorkbook wb = new HSSFWorkbook(myFile);
            HSSFSheet sheet = wb.getSheet("movies");

            HSSFCell cell;
            HSSFRow row;

            System.out.println("Cantidad de loops");

            System.out.println("" + sheet.getLastRowNum());

            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                String strCell = "";
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    strCell += cell.toString();
                }
                System.out.println("Valor: " + strCell);
            }
            System.out.println("Finalizado");

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
}

package adminpelis;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Jose
 */
public class EjecutarPeli {
    
    private DataFormatter dataFormat = new DataFormatter();
    private DB_Pelis db_service = new DB_Pelis();
    
    private ArrayList<Pelicula> listaPelis = new ArrayList<>();
    private ArrayList<String> listaCategoria = new ArrayList<>();
    
    private String[] listado, partirCadena, nombrePeli, categoria;
    
    private String completeCell;
    private int year;
    
    public void excelContent(Sheet sheet){
        listado = new String[sheet.getLastRowNum() + 1];
            
            for(Row row : sheet){
                completeCell = "";
                for(Cell cell : row){
                    /*
                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());    
                    System.out.println(cellRef.formatAsString());
                    
                    $A$123
                    -
                    $B$123
                    -
                    $C$123
                    
                    System.out.println("-");
                    */
                    
                    String text = dataFormat.formatCellValue(cell);
                    completeCell += text;
                }
                listado[row.getRowNum()] = completeCell;
                System.out.println(completeCell);
                //124::Star Maker The (Uomo delle stelle L') (1995)::Drama
            }
        System.out.println("");
        System.out.println("Cargando datos a la DB...");
        addPeli(listado);
    }
    
    private void addPeli(String[] listado){
        for (int i = 0; i < listado.length; i++) {
            partirCadena = listado[i].split("::");
            nombrePeli = partirCadena[1].split("\\(");
            categoria = partirCadena[2].split("\\|");
            if(nombrePeli.length == 3){
                nombrePeli[0] += ("(" + nombrePeli[1]);
                year = Integer.parseInt(nombrePeli[2].split("\\)")[0]);
            } else if (nombrePeli.length > 3){
                nombrePeli[0] += ("(" + nombrePeli[1] + "(" + nombrePeli[2]);
                year = Integer.parseInt(nombrePeli[3].split("\\)")[0]);
            } else {
                 year = Integer.parseInt(nombrePeli[1].split("\\)")[0]);
            }
            
            for (String categoria1 : categoria) {
                if(listaCategoria.isEmpty()){
                    listaCategoria.add(categoria1);
                } else{
                    if(!listaCategoria.contains(categoria1)){
                        listaCategoria.add(categoria1);
                    }
                }
            }
            
            listaPelis.add( new Pelicula(Integer.parseInt(partirCadena[0]), year, nombrePeli[0], categoria));
        }
        sendTablePelicula();
        sendTableCategoria();
        sendTablePeli_Cat();
        System.out.println("¡Datos almacenados exitosamente!");
        System.out.println("");
    }
 
    private void sendTablePelicula(){
        for(int i = 0; i < listado.length; i++){
            db_service.llenarTablaPelicula(listaPelis.get(i).getId(), listaPelis.get(i).getNombre(), listaPelis.get(i).getAnnio());
        }
    }
    
    private void sendTableCategoria(){
       listaCategoria.forEach((c) -> { db_service.llenarTablaCategoria(c); });
    }
    
    private void sendTablePeli_Cat(){
        for(int i = 0; i < listado.length; i++){
            for(String c : listaPelis.get(i).getCategorias()){
                db_service.llenarTablaPeli_Cat(listaPelis.get(i).getId(), db_service.obtener_IdCategori(c));
            }
        }
    }
    
    public void seePeli(int id){
        System.out.println("Id: " + listaPelis.get(id).getId());
        System.out.println("Pelicula: " + listaPelis.get(id).getNombre());
        System.out.print("Categorias: ");
        for(String c : listaPelis.get(id).getCategorias()){
            System.out.print("|" + c);
        }
        System.out.println();
    }

}

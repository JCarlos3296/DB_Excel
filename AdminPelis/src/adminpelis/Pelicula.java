package adminpelis;

/**
 *
 * @author Jose
 */
public class Pelicula {
    private int id;
    private int annio;
    private String nombre;
    private String[] categorias;

    public Pelicula(int id, int annio, String nombre, String[] categorias) {
        this.id = id;
        this.annio = annio;
        this.nombre = nombre;
        this.categorias = categorias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnio() {
        return annio;
    }

    public void setAnnio(int annio) {
        this.annio = annio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String[] getCategorias() {
        return categorias;
    }

    public void setCategorias(String[] categorias) {
        this.categorias = categorias;
    }
    
    
    
    
}

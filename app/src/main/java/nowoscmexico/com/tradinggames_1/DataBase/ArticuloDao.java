package nowoscmexico.com.tradinggames_1.DataBase;

/**
 * Created by vera_john on 21/02/17.
 */

public class ArticuloDao {

    String id;
    String titulo;
    String descripcion;
    String categoria;
    String pathfoto;
    String timeup;
    String idusuario;

    public ArticuloDao(String id, String titulo, String descripcion, String categoria, String pathfoto, String timeup, String idusuario) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.pathfoto = pathfoto;
        this.timeup = timeup;
        this.idusuario = idusuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPathfoto() {
        return pathfoto;
    }

    public void setPathfoto(String pathfoto) {
        this.pathfoto = pathfoto;
    }

    public String getTimeup() {
        return timeup;
    }

    public void setTimeup(String timeup) {
        this.timeup = timeup;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }
}

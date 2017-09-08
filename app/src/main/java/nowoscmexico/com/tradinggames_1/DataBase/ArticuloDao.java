package nowoscmexico.com.tradinggames_1.DataBase;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vera_john on 21/02/17.
 */

public class ArticuloDao {

    String id;
    String titulo;
    String descripcion;
    String categoria;
    String foto;
    String time;
    String idusuario;

    Bitmap iamgentemp;

    public ArticuloDao(String id, String titulo, String descripcion, String categoria, String pathfoto, String timeup, String idusuario) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.foto = pathfoto;
        this.time = timeup;
        this.idusuario = idusuario;
    }

    public ArticuloDao() {

    }

    public Bitmap getIamgentemp() {
        return iamgentemp;
    }

    public void setIamgentemp(Bitmap iamgentemp) {
        this.iamgentemp = iamgentemp;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String pathfoto) {
        this.foto = pathfoto;
    }

    public String getTimeup() {
        return time;
    }

    public void setTimeup(String timeup) {
        this.time = timeup;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("titulo", titulo);
        result.put("descripcion", descripcion);
        result.put("categoria", categoria);
        result.put("foto", foto);
        result.put("time", time);
        result.put("idusuario", idusuario);

        return result;
    }
}

package nowoscmexico.com.tradinggames_1.DataBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vera_john on 20/02/17.
 */

public class UsuarioDao {

    String id;
    String nombre;
    String telefono;
    String correo;
    String pass;
    String activo;

    public UsuarioDao(){

    }

    public UsuarioDao(String id, String nombre, String telefono, String correo, String pass, String activo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.pass = pass;
        this.activo = activo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("nombre", nombre);
        result.put("telefono", telefono);
        result.put("correo", correo);
        result.put("pass", pass);
        result.put("activo", activo);

        return result;
    }
}

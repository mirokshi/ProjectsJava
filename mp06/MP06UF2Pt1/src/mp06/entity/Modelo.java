package mp06.entity;
// Generated 6 mar. 2019 20:56:33 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Modelo generated by hbm2java
 */
public class Modelo  implements java.io.Serializable {


     private Integer id;
     private String descripcion;
     private Set vehiculoses = new HashSet(0);

    public Modelo() {
    }

	
    public Modelo(String descripcion) {
        this.descripcion = descripcion;
    }
    public Modelo(String descripcion, Set vehiculoses) {
       this.descripcion = descripcion;
       this.vehiculoses = vehiculoses;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Set getVehiculoses() {
        return this.vehiculoses;
    }
    
    public void setVehiculoses(Set vehiculoses) {
        this.vehiculoses = vehiculoses;
    }




}


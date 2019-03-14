package mp06.entity;
// Generated 6 mar. 2019 20:56:33 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Mantenimiento generated by hbm2java
 */
public class Mantenimiento  implements java.io.Serializable {


     private Integer id;
     private Piloto piloto;
     private Vehiculos vehiculos;
     private Date fechaMantenimiento;

    public Mantenimiento() {
    }

    public Mantenimiento(Piloto piloto, Vehiculos vehiculos, Date fechaMantenimiento) {
       this.piloto = piloto;
       this.vehiculos = vehiculos;
       this.fechaMantenimiento = fechaMantenimiento;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Piloto getPiloto() {
        return this.piloto;
    }
    
    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }
    public Vehiculos getVehiculos() {
        return this.vehiculos;
    }
    
    public void setVehiculos(Vehiculos vehiculos) {
        this.vehiculos = vehiculos;
    }
    public Date getFechaMantenimiento() {
        return this.fechaMantenimiento;
    }
    
    public void setFechaMantenimiento(Date fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }




}


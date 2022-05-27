package Mooving.MUgitu.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "estacion")
public class Estacion {
    @Id
    @GenericGenerator(name="estacion" , strategy="increment")
    @GeneratedValue(generator="estacion")
    @Column(name = "estacion_id", nullable = false)
    private Long id;

    @Column(name = "nombre", length = 128)
    private String nombre;

    @Column(name = "plazas")
    private Integer plazas;

    @Column(name = "activa")
    private Boolean activa;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "ia", nullable = false)
    private Boolean ia = false;

    public Boolean getIa() {
        return ia;
    }

    public void setIa(Boolean ia) {
        this.ia = ia;
    }

    public Estacion(){}

    public Estacion(Long id, String nombre, Integer plazas, Boolean activa, Double latitud, Double longitud, boolean ia) {
        this.id = id;
        this.nombre = nombre;
        this.plazas = plazas;
        this.activa = activa;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ia = ia;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPlazas() {
        return plazas;
    }

    public void setPlazas(Integer plazas) {
        this.plazas = plazas;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}
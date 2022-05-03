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

/*
  TODO [JPA Buddy] create field to map the 'coordenadas' column
   Available actions: Define target Java type | Uncomment as is | Remove column mapping
  @Column(name = "coordenadas", columnDefinition = "GEOMETRY(65535)")
  private Object coordenadas;
*/
}
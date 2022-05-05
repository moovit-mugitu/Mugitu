package Mooving.MUgituApi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_averia")
public class TipoAveria {
    @Id
    @Column(name = "tipo_averia_id", nullable = false)
    private Integer id;

    @Column(name = "descripcion", length = 64)
    private String descripcion;

    public TipoAveria(){}

    public TipoAveria(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
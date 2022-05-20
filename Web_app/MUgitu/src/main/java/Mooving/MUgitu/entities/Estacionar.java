package Mooving.MUgitu.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "estacionar")
public class Estacionar {
    @Id
    @GenericGenerator(name="estacionar" , strategy="increment")
    @GeneratedValue(generator="estacionar")
    @Column(name = "estacionar_id", nullable = false)
    private Long estacionarId;

    @ManyToOne
    @JoinColumn(name = "estacion_id")
    private Estacion estacion;

    @ManyToOne
    @JoinColumn(name = "bici_id")
    private Bici bici;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_fin")
    private Date fechaFin;

    public Estacionar() {
    }

    public Estacionar(Long estacionarId, Estacion estacion, Bici bici, Date fechaInicio, Date fechaFin) {
        this.estacionarId = estacionarId;
        this.estacion = estacion;
        this.bici = bici;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getEstacionarId() {
        return estacionarId;
    }

    public void setEstacionarId(Long estacionarId) {
        this.estacionarId = estacionarId;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public Bici getBici() {
        return bici;
    }

    public void setBici(Bici bici) {
        this.bici = bici;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}

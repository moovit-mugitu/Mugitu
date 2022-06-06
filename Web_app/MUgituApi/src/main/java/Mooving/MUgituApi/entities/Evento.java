package Mooving.MUgituApi.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name= "evento")
public class Evento {
    @Id
    @GenericGenerator(name="evento" , strategy="increment")
    @GeneratedValue(generator="evento")
    @Column(name = "evento_id", nullable = false)
    private Long eventoId;

    @ManyToOne
    @JoinColumn(name = "bici_id")
    private Bici bici;

    @Column(name = "estado")
    private boolean estado;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

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

    public Evento(){
        this.fecha = new Date();
    }

    public Evento(Long eventoId, Bici bici, boolean estado, Date fecha) {
        this.eventoId = eventoId;
        this.bici = bici;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public Bici getBici() {
        return bici;
    }

    public void setBici(Bici bici) {
        this.bici = bici;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

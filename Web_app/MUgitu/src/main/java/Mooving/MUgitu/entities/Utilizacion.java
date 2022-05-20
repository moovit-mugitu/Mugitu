package Mooving.MUgitu.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name= "utilizacion")
public class Utilizacion {
    @Id
    @GenericGenerator(name="utiliza" , strategy="increment")
    @GeneratedValue(generator="utiliza")
    @Column(name = "utiliza_id", nullable = false)
    private Long utilizaId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    @ManyToOne
    @JoinColumn(name = "bici_id")
    private Bici bici;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_fin")
    private Date fechaFin;

    public Utilizacion(){
        this.fechaInicio = new Date();
        this.fechaFin = null;
    }

    public Utilizacion(Long utilizaId, Usuario user, Bici bici, Date fechaInicio, Date fechaFin) {
        this.utilizaId = utilizaId;
        this.user = user;
        this.bici = bici;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getUtilizaId() {
        return utilizaId;
    }

    public void setUtilizaId(Long utilizaId) {
        this.utilizaId = utilizaId;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
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

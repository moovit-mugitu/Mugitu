package Mooving.MUgituApi.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name= "notificacion_averia")
public class NotificacionAveria {
    @Id
    @GenericGenerator(name="notificacion" , strategy="increment")
    @GeneratedValue(generator="notificacion")
    @Column(name = "notificacion_id", nullable = false)
    private Long notificacionId;

    @ManyToOne
    @JoinColumn(name = "bici_id")
    private Bici bici;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "tipo_averia")
    private int tipoAveria;

    public NotificacionAveria(){
        this.fecha = new Date();
    }

    public NotificacionAveria(Long notificacionId, Bici bici, Usuario user, Date fecha, int tipoAveria) {
        this.notificacionId = notificacionId;
        this.bici = bici;
        this.user = user;
        this.fecha = fecha;
        this.tipoAveria = tipoAveria;
    }

    public Long getNotificacionId() {
        return notificacionId;
    }

    public void setNotificacionId(Long notificacionId) {
        this.notificacionId = notificacionId;
    }

    public Bici getBici() {
        return bici;
    }

    public void setBici(Bici bici) {
        this.bici = bici;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTipoAveria() {
        return tipoAveria;
    }

    public void setTipoAveria(int tipoAveria) {
        this.tipoAveria = tipoAveria;
    }
}

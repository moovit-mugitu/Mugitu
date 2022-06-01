package Mooving.MUgituApi.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notificacion_averia")
public class NotificacionAveria {
    @Id
    @GenericGenerator(name = "notificacion", strategy = "increment")
    @GeneratedValue(generator = "notificacion")
    @Column(name = "notificacion_id", nullable = false)
    private Long notificacionId;

    @ManyToOne
    @JoinColumn(name = "bici_id")
    private Bici bici;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "tipo_averia")
    private int tipoAveria;

    @Column(name = "nueva")
    private Boolean nueva;

    @Column(name = "resuelta")
    private Boolean resuelta;

    @Column(name = "mensaje", length = 128)
    private String mensaje;

    public NotificacionAveria() {
        this.fecha = new Date();
        this.nueva = true;
        this.resuelta = false;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getResuelta() {
        return resuelta;
    }

    public void setResuelta(Boolean resuelta) {
        this.resuelta = resuelta;
    }

    public Boolean getNueva() {
        return nueva;
    }

    public void setNueva(Boolean nueva) {
        this.nueva = nueva;
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

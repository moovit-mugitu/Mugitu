package moovit.mugitu.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tipo_usuario")
public class TipoUsuario {
    public final static int ADMIN = 1;
    public final static int USER = 2;

    @Id
    @GenericGenerator(name="tipo_usuario" , strategy="increment")
    @GeneratedValue(generator="tipo_usuario")
    @Column(name = "tipo_user_id", nullable = false)
    private Integer id;

    @Column(name = "descripcion", length = 18)
    private String descripcion;

    public TipoUsuario(){}

    public TipoUsuario(Integer id, String descripcion) {
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
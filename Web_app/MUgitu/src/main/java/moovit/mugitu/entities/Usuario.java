package moovit.mugitu.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name= "usuario")
public class Usuario {
    @Id
    @GenericGenerator(name="user" , strategy="increment")
    @GeneratedValue(generator="user")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "correo")
    private String correo;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "DNI")
    private String DNI;

    @Column(name = "sexo")
    private Integer sexo;

    @Column(name = "password")
    private String password;

    @Column(name = "fecha_nacimiento")
    private Date fecha_nacimiento;

    @Column(name = "verificado")
    private boolean verificado;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id")
    private TipoUsuario tipo_usuario;

    private enum Sex {
        Woman,
        Man,
        NoBinary;
    }

    public Usuario(){
    }

    public Usuario(Long userId, String nombre, String apellidos, String correo, String telefono, String DNI, Integer sexo, String password, Date fecha_nacimiento, Boolean verificado, TipoUsuario tipo_usuario) {
        this.userId = userId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.DNI = DNI;
        this.sexo = sexo;
        this.password = password;
        this.fecha_nacimiento = fecha_nacimiento;
        this.verificado = verificado;
        this.tipo_usuario = tipo_usuario;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public TipoUsuario getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(TipoUsuario tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }
}

package Mooving.MUgitu.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "tarjeta_pago")
    private String tarjeta_pago;

    @Column(name = "numero_seguridad")
    private String numero_seguridad;

    @Column(name = "fecha_caducidad")
    private Date fecha_caducidad;

    @Column(name = "sexo")
    private Integer sexo;

    @Column(name = "password")
    private String password;

    @Column(name = "fecha_nacimiento")
    private Date fecha_nacimiento;

    @Column(name = "tipo_usuario")
    private Integer tipo_usuario;

    public Usuario(){

    }

    public Usuario(Long userId, String nombre, String apellidos, String correo, String telefono, String DNI, String tarjeta_pago, String numero_seguridad, Date fecha_caducidad, Integer sexo, String password, Date fecha_nacimiento, Integer tipo_usuario) {
        this.userId = userId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.DNI = DNI;
        this.tarjeta_pago = tarjeta_pago;
        this.numero_seguridad = numero_seguridad;
        this.fecha_caducidad = fecha_caducidad;
        this.sexo = sexo;
        this.password = password;
        this.fecha_nacimiento = fecha_nacimiento;
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

    public String getTarjeta_pago() {
        return tarjeta_pago;
    }

    public void setTarjeta_pago(String tarjeta_pago) {
        this.tarjeta_pago = tarjeta_pago;
    }

    public String getNumero_seguridad() {
        return numero_seguridad;
    }

    public void setNumero_seguridad(String numero_seguridad) {
        this.numero_seguridad = numero_seguridad;
    }

    public Date getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(Date fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
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

    public Integer getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(Integer tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}

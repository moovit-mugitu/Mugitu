package tmpPackage.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name= "bici")
public class Bici {
    @Id
    @GenericGenerator(name="bici" , strategy="increment")
    @GeneratedValue(generator="bici")
    @Column(name = "bici_id", nullable = false)
    private Long biciId;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "estado")
    private boolean estado;

    @Column(name = "electrica")
    private Boolean electrica;

    @Column(name = "SOC")
    private Integer SOC;

    public Bici() {
        this.electrica = false;
        this.SOC = 0;
    }

    public Bici(Long biciId, String modelo, boolean estado, Boolean electrica, Integer SOC) {
        this.biciId = biciId;
        this.modelo = modelo;
        this.estado = estado;
        this.electrica = electrica;
        this.SOC = SOC;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Boolean getElectrica() {
        return electrica;
    }

    public void setElectrica(Boolean electrica) {
        this.electrica = electrica;
    }

    public Integer getSOC() {
        return SOC;
    }

    public void setSOC(Integer SOC) {
        this.SOC = SOC;
    }

    public Long getBiciId() {
        return biciId;
    }

    public void setBiciId(Long id) {
        this.biciId = id;
    }
}

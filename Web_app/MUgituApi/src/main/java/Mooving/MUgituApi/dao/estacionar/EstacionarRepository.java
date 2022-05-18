package Mooving.MUgituApi.dao.estacionar;


import Mooving.MUgituApi.entities.Bici;
import Mooving.MUgituApi.entities.Estacion;
import Mooving.MUgituApi.entities.Estacionar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstacionarRepository extends JpaRepository<Estacionar, Long> {
    List<Estacionar> getEstacionarByEstacion(Estacion estacion);
    List<Estacionar> getEstacionarByBici(Bici bici);
    List<Estacionar> getEstacionarByFechaFinIsNull();
}

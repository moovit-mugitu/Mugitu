package Mooving.MUgituApi.dao.estacionar;

import Mooving.MUgituApi.entities.Bici;
import Mooving.MUgituApi.entities.Estacion;
import Mooving.MUgituApi.entities.Estacionar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface EstacionarRepository extends JpaRepository<Estacionar, Long> {
    List<Estacionar> getEstacionarByBici(Bici bici);
    List<Estacionar> getEstacionarByEstacion(Estacion estacion);
    List<Estacionar> getEstacionarByFechaFinIsNull();
    List<Estacionar> getEstacionarByFechaFinIsNotNull();
    List<Estacionar> getEstacionarByFechaFinIsNullAndEstacion(Estacion estacion);
    Estacionar getEstacionarByBiciBiciIdAndFechaFinIsNull(long id);
}

package Mooving.MUgituApi.dao.evento;

import Mooving.MUgituApi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    @Query(
            value = "select e.* " +
                    "from evento e " +
                    "join(SELECT e.bici_id, max(e.fecha) fecha " +
                        "from evento e " +
                        "group by e.bici_id) ids on ids.bici_id = e.bici_id and e.fecha = ids.fecha " +
                    "join utilizacion u on e.bici_id = u.bici_id and u.fecha_fin is null ",
            nativeQuery = true)
    List<Evento> getUltimosEventosPorBici();
}

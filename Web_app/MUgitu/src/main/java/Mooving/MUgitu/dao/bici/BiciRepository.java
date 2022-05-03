package Mooving.MUgitu.dao.bici;

import Mooving.MUgitu.entities.Bici;
import Mooving.MUgitu.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiciRepository extends JpaRepository<Bici, Long> {
    List<Bici> getBicisByElectrica(Boolean electrica);
}

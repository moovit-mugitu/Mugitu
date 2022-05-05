package Mooving.MUgituApi.dao.bici;

import Mooving.MUgituApi.entities.Bici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiciRepository extends JpaRepository<Bici, Long> {
    List<Bici> getBicisByElectrica(Boolean electrica);
}

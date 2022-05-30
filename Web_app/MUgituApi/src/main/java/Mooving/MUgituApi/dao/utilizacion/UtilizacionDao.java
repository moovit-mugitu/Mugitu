package Mooving.MUgituApi.dao.utilizacion;

import Mooving.MUgituApi.entities.Utilizacion;

import java.util.List;

public interface UtilizacionDao {
    List<Utilizacion> getAllUtilizacions();

    Utilizacion getUtilizacion(long id);

    void editUtilizacion(Utilizacion utilizacion);

    void deleteUtilizacion(long id);

    void deleteUtilizacion(Utilizacion utilizacion);

    Utilizacion addUtilizacion(Utilizacion utilizacion);

    List<Utilizacion> getUtilizacionSinFin();

    Utilizacion finishUtilizacion(Long biciId, long userId);
}

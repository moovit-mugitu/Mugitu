package Mooving.MUgituApi.dao.evento;

import Mooving.MUgituApi.entities.Bici;
import Mooving.MUgituApi.entities.Evento;

import java.util.List;

public interface EventoDao {
     List<Evento> getAllEventos();
     Evento getEvento(long id);
     void editEvento(Evento evento);
     void deleteEvento(long id);
     void deleteEvento(Evento evento);
     Evento addEvento(Evento evento);
     List<Evento> getUltimosEventosByEstado(Bici.Estados estado);
}

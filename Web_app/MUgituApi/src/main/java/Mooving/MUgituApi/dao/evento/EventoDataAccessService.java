package Mooving.MUgituApi.dao.evento;

import Mooving.MUgituApi.entities.Bici;
import Mooving.MUgituApi.entities.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoDataAccessService implements EventoDao {

    @Autowired
    private EventoRepository repository;

    @Override
    public List<Evento> getAllEventos() {
        return repository.findAll();
    }

    @Override
    public Evento getEvento(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editEvento(Evento evento) {
        repository.save(evento);
    }

    @Override
    public void deleteEvento(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteEvento(Evento evento) {
        repository.delete(evento);
    }

    @Override
    public Evento addEvento(Evento evento) {
        return repository.save(evento);
    }

    @Override
    public List<Evento> getUltimosEventosByEstado(Bici.Estados estado) {
        List<Evento> eventos = repository.getUltimosEventosPorBici();
        List<Evento> eventosByEstado = eventos.stream()
                .filter(e -> estado.getValue() == e.getEstado())
                .collect(Collectors.toList());
        return eventosByEstado;
    }
}

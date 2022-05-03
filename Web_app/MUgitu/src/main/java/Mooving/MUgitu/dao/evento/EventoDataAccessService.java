package Mooving.MUgitu.dao.evento;

import Mooving.MUgitu.entities.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addEvento(Evento evento) {
        repository.save(evento);
    }
}

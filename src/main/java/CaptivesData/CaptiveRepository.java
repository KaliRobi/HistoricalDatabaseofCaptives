package CaptivesData;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// Repository to connect to the databse

public class CaptiveRepository implements CrudRepository<Integer, Captive> {
    @Override
    public <S extends Integer> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Integer> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Integer> findById(Captive captive) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Captive captive) {
        return false;
    }

    @Override
    public Iterable<Integer> findAll() {
        return null;
    }

    @Override
    public Iterable<Integer> findAllById(Iterable<Captive> captives) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Captive captive) {

    }

    @Override
    public void delete(Integer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Captive> captives) {

    }

    @Override
    public void deleteAll(Iterable<? extends Integer> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

package projectH.HistoricalDatabaseofCaptives.LRUCache;

import java.util.Optional;

public class LRUCache<K, V> implements  Cache<K, V>{
    @Override
    public boolean set(K key, V value) {
        return false;
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }
}

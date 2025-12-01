package nsu.sber.db.repository.redis;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.menu.Menu;
import nsu.sber.domain.port.repository.redis.MenuRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisMenuRepository implements MenuRepositoryPort {
    private static final String KEY_PREFIX = "menu";

    private final RedisTemplate<String, Menu> menuRedisTemplate;

    @Value("${redis.menu.ttl-hours:2}")
    private int ttlHours;

    @Override
    public void save(int terminalGroupId, Menu menu) {
        menuRedisTemplate.opsForValue().set(getKey(terminalGroupId), menu, ttlHours, TimeUnit.HOURS);
    }

    @Override
    public Optional<Menu> findByTerminalGroupId(int terminalGroupId) {
        return Optional.ofNullable(menuRedisTemplate.opsForValue().get(getKey(terminalGroupId)));
    }

    @Override
    public void delete(int terminalGroupId) {
        menuRedisTemplate.delete(getKey(terminalGroupId));
    }

    private String getKey(int terminalGroupId) {
        return "%s:%s".formatted(KEY_PREFIX, terminalGroupId);
    }
}

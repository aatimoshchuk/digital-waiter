package nsu.sber.db.repository.redis;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.cart.Cart;
import nsu.sber.domain.port.repository.redis.CartRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisCartRepository implements CartRepositoryPort {
    private static final String KEY_PREFIX = "cart";

    private final RedisTemplate<String, Cart> cartRedisTemplate;

    @Value("${redis.cart.ttl-hours:2}")
    private int ttlHours;

    @Override
    public void save(String tableId, Cart cart) {
        cartRedisTemplate.opsForValue().set(getKey(tableId), cart, ttlHours, TimeUnit.HOURS);
    }

    @Override
    public Optional<Cart> findByTableId(String tableId) {
        return Optional.ofNullable(cartRedisTemplate.opsForValue().get(getKey(tableId)));
    }

    @Override
    public void delete(String tableId) {
        cartRedisTemplate.delete(getKey(tableId));
    }

    private String getKey(String tableId) {
        return "%s:%s".formatted(KEY_PREFIX, tableId);
    }

}

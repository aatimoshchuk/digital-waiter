package nsu.sber.cache.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.cart.Cart;
import nsu.sber.domain.port.CartRepositoryPort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisCartRepository implements CartRepositoryPort {

    private final RedisTemplate<String, Cart> redisTemplate;

    @Override
    public void save(String tableId, Cart cart) {
        redisTemplate.opsForValue().set(tableId, cart, 2, TimeUnit.HOURS);
    }

    @Override
    public Cart findByTableId(String tableId) {
        return redisTemplate.opsForValue().get(tableId);
    }

    @Override
    public void delete(String tableId) {
        redisTemplate.delete(tableId);
    }
}

package nsu.sber.domain.port.repository.redis;

import nsu.sber.domain.model.cart.Cart;

import java.util.Optional;

public interface CartRepositoryPort {

    void save(String tableId, Cart cart);

    Optional<Cart> findByTableId(String tableId);

    void delete(String tableId);
}

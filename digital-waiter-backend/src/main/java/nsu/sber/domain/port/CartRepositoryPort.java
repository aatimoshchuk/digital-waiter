package nsu.sber.domain.port;

import nsu.sber.domain.model.cart.Cart;

public interface CartRepositoryPort {

    void save(String tableId, Cart cart);

    public Cart findByTableId(String tableId);

    void delete(String tableId);
}

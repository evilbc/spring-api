package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.Product;
import com.codewithmosh.store.products.ProductNotFoundException;
import com.codewithmosh.store.products.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto findCartById(UUID id) {
        Cart cart = cartRepository.getCartWithItems(id).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        return cartMapper.toDto(cart);
    }

    public CartDto createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        CartItem item = cart.addItem(product);
        cartRepository.save(cart);

        return cartMapper.toDto(item);
    }

    public CartItemDto updateCartItem(UUID cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        CartItem cartItem = cart.findCartItem(productId).orElse(null);
        if (cartItem == null) {
            throw new ProductNotFoundException();
        }
        cartItem.setQuantity(quantity);

        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void removeCartItem(UUID cartId, Long productId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        cart.clear();
        cartRepository.save(cart);
    }
}

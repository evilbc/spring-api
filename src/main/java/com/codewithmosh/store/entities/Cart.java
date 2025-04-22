package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        return items.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<CartItem> findCartItem(Long productId) {
        return items.stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst();
    }

    public CartItem addItem(Product product) {
        CartItem item = findCartItem(product.getId()).orElse(null);
        if (item != null) {
            item.addQuantity();
        } else {
            item = new CartItem();
            item.setProduct(product);
            item.setCart(this);
            item.setQuantity(1);
            items.add(item);
        }
        return item;
    }

    public void removeItem(Long productId) {
        CartItem cartItem = findCartItem(productId).orElse(null);
        if (cartItem != null) {
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
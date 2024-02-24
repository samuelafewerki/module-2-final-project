package com.techelevator.controller;

import com.techelevator.model.CartItem;
import com.techelevator.model.ShoppingCart;
import com.techelevator.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value="/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping(value="")
    public ShoppingCart getUsersCart(Principal userPrincipal) {
        return cartService.getShoppingCartForUser(userPrincipal);
    }

    @PostMapping(value="/items")
    public void addToCart(Principal userPrincipal, @RequestBody CartItem cartItem) {
        cartService.addToCart(userPrincipal, cartItem);
    }

    @DeleteMapping(value="/items/{itemId}")
    public void removeFromCart(Principal userPrincipal, @PathVariable int itemId) {
        cartService.removeFromCart(userPrincipal, itemId);
    }

    @DeleteMapping(value="/clear")
    public void clearCart(Principal userPrincipal) {
        cartService.clearCart(userPrincipal);
    }
}

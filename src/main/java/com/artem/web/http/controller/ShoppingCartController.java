package com.artem.web.http.controller;

import com.artem.dto.CustomerReadDto;
import com.artem.dto.ShoppingCartCreateEditDto;
import com.artem.service.CustomerService;
import com.artem.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/shopping-cart")
@Controller
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public String find(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        return shoppingCartService.findBy(userDetails)
                .map(shoppingCart -> {
                    model.addAttribute("shoppingCart", shoppingCart);
                    return "shoppingCart/shoppingCart";
                }).orElseGet(() -> "shoppingCart/shoppingCartEmpty");
    }

    @PostMapping("/createShoppingCart")
    public String createShoppingCart(@Validated ShoppingCartCreateEditDto shoppingCart,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("shoppingCart", shoppingCart);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/shopping-cart/create";
        }

        return "redirect:/shopping-cart/" + shoppingCartService.create(shoppingCart).getId();
    }


    @GetMapping("/update")
    public String update(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        return shoppingCartService.findBy(userDetails)
                .map(shoppingCart -> {
                    model.addAttribute("shoppingCart", shoppingCart);
                    return "customer/customerEdit";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/updateShoppingCart")
    public String updateShoppingCart(@AuthenticationPrincipal UserDetails userDetails,
                                     @Validated ShoppingCartCreateEditDto shoppingCart,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("shoppingCart", shoppingCart);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/shopping-cart/{id}/update";
        }

        return shoppingCartService.update(userDetails, shoppingCart)
                .map(it -> "redirect:/shopping-cart/{id}/update")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/delete")
    public String delete(@AuthenticationPrincipal UserDetails userDetails) {
        if (!shoppingCartService.delete(userDetails)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/shopping-cart";
    }

    @GetMapping("{id}/put")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public String put(@PathVariable("id") Long productId,
                      @AuthenticationPrincipal UserDetails userDetails) {
        shoppingCartService.putItem(productId, userDetails.getUsername());

        return "redirect:/products";
    }
}

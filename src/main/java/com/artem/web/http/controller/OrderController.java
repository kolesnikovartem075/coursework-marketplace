package com.artem.web.http.controller;


import com.artem.database.entity.OrderStatus;
import com.artem.database.entity.Role;
import com.artem.dto.*;
import com.artem.service.OrderService;
import com.artem.service.PaymentMethodService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/orders")
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public String findAll(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        var orders = getOrders(userDetails);
        model.addAttribute("orders", orders);

        return "order/orders";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("statusList", OrderStatus.values());
                    return "order/order";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(@AuthenticationPrincipal UserDetails userDetails, Model model,
                         @ModelAttribute OrderCreateEditDto order) {
        var shoppingCartReadDto = getShoppingCartReadDto(userDetails);
        var paymentMethod = getPaymentMethodReadDto(userDetails);

        model.addAttribute("shoppingCart", shoppingCartReadDto);
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("order", order);

        return "order/orderCreate";
    }

    @PostMapping("/createOrder")
    public String createOrder(PaymentMethodCreateEditDto paymentMethod,
                              @Validated OrderCreateEditDto order,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("order", order);
            redirectAttributes.addFlashAttribute("paymentMethod", paymentMethod);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/shopping-cart/create";
        }

        return "redirect:/orders/" + orderService.createOrder(order).getId();
    }


    @GetMapping("{id}/update")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String update(@PathVariable Long id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("statusList", OrderStatus.values());
                    return "order/orderEdit";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping("{id}/updateOrder")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String updateOrder(@PathVariable Long id,
                              @Validated OrderCreateEditDto order,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("order", order);
            redirectAttributes.addFlashAttribute("statusList", OrderStatus.values());
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/orders/{id}/update";
        }

        return orderService.update(id, order)
                .map(it -> "redirect:/orders/{id}/update")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!orderService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/orders";
    }

    private ShoppingCartReadDto getShoppingCartReadDto(UserDetails userDetails) {
        return shoppingCartService.findBy(userDetails)
                .orElseThrow();
    }

    private PaymentMethodReadDto getPaymentMethodReadDto(UserDetails userDetails) {
        return paymentMethodService.findByCustomer(userDetails)
                .orElse(null);
    }

    private List<OrderReadDto> getOrders(UserDetails userDetails) {
        return userDetails.getAuthorities().contains(Role.MANAGER)
                ? orderService.findAll()
                : orderService.findAllByCustomer(userDetails);
    }

}

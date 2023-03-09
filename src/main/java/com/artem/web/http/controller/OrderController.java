package com.artem.web.http.controller;


import com.artem.dto.OrderCreateEditDto;
import com.artem.dto.PaymentMethodCreateEditDto;
import com.artem.dto.PaymentMethodReadDto;
import com.artem.dto.ShoppingCartReadDto;
import com.artem.mapper.OrderCreateEditMapper;
import com.artem.service.OrderService;
import com.artem.service.PaymentMethodService;
import com.artem.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/order")
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final PaymentMethodService paymentMethodService;

    @GetMapping("{id}")
    public String find(@PathVariable Long id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
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

        return "redirect:/order/" + orderService.createOrder(order).getId();
    }


    @GetMapping("/update")
    public String update(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        return orderService.findBy(userDetails)
                .map(order -> {
                    model.addAttribute("order", order);
                    return "order/orderEdit";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!orderService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/products";
    }

    private ShoppingCartReadDto getShoppingCartReadDto(UserDetails userDetails) {
        return shoppingCartService.findBy(userDetails)
                .orElseThrow();
    }

    private PaymentMethodReadDto getPaymentMethodReadDto(UserDetails userDetails) {
        return paymentMethodService.findByCustomer(userDetails)
                .orElseThrow();
    }
}

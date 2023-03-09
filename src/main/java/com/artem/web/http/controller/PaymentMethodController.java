package com.artem.web.http.controller;

import com.artem.dto.PaymentMethodCreateEditDto;
import com.artem.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/payment-method")
@Controller
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @PostMapping("/createUpdatePaymentMethod")
    public String createUpdate(@Validated PaymentMethodCreateEditDto paymentMethod,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("shoppingCart", paymentMethod);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/order/create";
        }
        paymentMethodService.findByCustomerId(paymentMethod.getCustomerId())
                .ifPresentOrElse(entity -> paymentMethodService.update(paymentMethod, entity.getId()), () -> paymentMethodService.create(paymentMethod));
        return "redirect:/order/create";
    }

}
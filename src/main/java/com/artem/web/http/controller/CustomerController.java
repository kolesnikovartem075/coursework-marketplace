package com.artem.web.http.controller;

import com.artem.dto.CustomerCreateEditDto;
import com.artem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/customers")
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("customers", customerService.findAll());

        return "customer/customers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer/customer";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(Model model,
                         @ModelAttribute("customer") CustomerCreateEditDto customer) {
        model.addAttribute("customer", customer);

        return "customer/customerCreate";
    }

    @PostMapping("/createCustomer")
    public String createCustomer(@Validated CustomerCreateEditDto customer,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/customers/create";
        }

        return "redirect:/customers/" + customerService.create(customer).getId();
    }


    @GetMapping("{id}/update")
    public String update(@PathVariable Long id, Model model) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer/customerEdit";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/updateCustomer")
    public String updateCustomer(@PathVariable Long id,
                                 @Validated CustomerCreateEditDto customer,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/customers/{id}/update";
        }

        return customerService.update(id, customer)
                .map(it -> "redirect:/customers/{id}/update")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!customerService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/customers";
    }
}

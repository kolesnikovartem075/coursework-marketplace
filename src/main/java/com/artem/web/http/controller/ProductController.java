package com.artem.web.http.controller;

import com.artem.dto.CustomerReadDto;
import com.artem.dto.ProductCreateEditDto;
import com.artem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequestMapping("/products")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String findAll(Model model, HttpSession session) {
        Long customerId = getCustomerIdFromSession(session);
        model.addAttribute("customerId", customerId);
        model.addAttribute("products", productService.findAll());

        return "product/products";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, HttpSession session) {
        return productService.findById(id)
                .map(product -> {
                    Long customerId = getCustomerIdFromSession(session);
                    model.addAttribute("customerId", customerId);
                    model.addAttribute("product", product);

                    return "product/product";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(Model model,
                         ProductCreateEditDto product) {
        model.addAttribute("product", product);

        return "product/productCreate";
    }

    @PostMapping("/createProduct")
    public String createProduct(@Validated ProductCreateEditDto product,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/products/create";
        }

        return "redirect:/products/" + productService.create(product).getId();
    }


    @GetMapping("{id}/update")
    public String update(@PathVariable Long id, Model model) {
        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    return "product/productEdit";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/updateProduct")
    public String updateProduct(@PathVariable Long id,
                                @Validated ProductCreateEditDto product,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/products/{id}/update";
        }

        return productService.update(id, product)
                .map(it -> "redirect:/products/{id}/update")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!productService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/products";
    }

    private static Long getCustomerIdFromSession(HttpSession session) {
        return Optional.ofNullable((CustomerReadDto) session.getAttribute("customer"))
                .map(CustomerReadDto::getId)
                .orElse(null);
    }
}

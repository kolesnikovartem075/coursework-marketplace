package com.artem.web.http.rest;

import com.artem.dto.CustomerCreateEditDto;
import com.artem.dto.CustomerReadDto;
import com.artem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerReadDto> findAll() {
        return customerService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerReadDto findById(@PathVariable Long id) {
        return customerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerReadDto create(@Validated @RequestBody CustomerCreateEditDto customer) {
        return customerService.create(customer);
    }

    @PutMapping("/{id}")
    public CustomerReadDto update(@PathVariable Long id,
                                  @Validated @RequestBody CustomerCreateEditDto customer) {
        return customerService.update(id, customer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return customerService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
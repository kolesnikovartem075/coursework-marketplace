package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.database.entity.PaymentMethod;
import com.artem.database.repository.CustomerRepository;
import com.artem.dto.PaymentMethodCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMethodCreateEditMapper implements Mapper<PaymentMethodCreateEditDto, PaymentMethod> {

    private final CustomerRepository customerRepository;

    @Override
    public PaymentMethod map(PaymentMethodCreateEditDto object) {
        PaymentMethod paymentMethod = new PaymentMethod();
        copy(object, paymentMethod);

        return paymentMethod;
    }

    @Override
    public PaymentMethod map(PaymentMethodCreateEditDto fromObject, PaymentMethod toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(PaymentMethodCreateEditDto object, PaymentMethod paymentMethod) {
        Customer customer = customerRepository.findById(object.getCustomerId()).orElseThrow();

        paymentMethod.setCustomer(customer);
        paymentMethod.setExpiryDate(object.getExpiryDate());
        paymentMethod.setAccountNumber(object.getAccountNumber());
    }
}

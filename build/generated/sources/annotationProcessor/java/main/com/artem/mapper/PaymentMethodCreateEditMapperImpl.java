package com.artem.mapper;

import com.artem.database.entity.PaymentMethod;
import com.artem.dto.PaymentMethodCreateEditDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
@Component
public class PaymentMethodCreateEditMapperImpl implements PaymentMethodCreateEditMapper {

    @Override
    public PaymentMethod map(PaymentMethodCreateEditDto paymentMethodCreateEditDto) {
        if ( paymentMethodCreateEditDto == null ) {
            return null;
        }

        PaymentMethod.PaymentMethodBuilder paymentMethod = PaymentMethod.builder();

        paymentMethod.accountNumber( paymentMethodCreateEditDto.getAccountNumber() );
        paymentMethod.expiryDate( paymentMethodCreateEditDto.getExpiryDate() );

        return paymentMethod.build();
    }

    @Override
    public PaymentMethod map(PaymentMethodCreateEditDto paymentMethodCreateEditDto, PaymentMethod entity) {
        if ( paymentMethodCreateEditDto == null ) {
            return entity;
        }

        entity.setAccountNumber( paymentMethodCreateEditDto.getAccountNumber() );
        entity.setExpiryDate( paymentMethodCreateEditDto.getExpiryDate() );

        return entity;
    }
}

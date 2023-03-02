package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.dto.CustomerReadDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
public class CustomerReadMapperImpl implements CustomerReadMapper {

    @Override
    public CustomerReadDto map(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        String phone = null;

        id = customer.getId();
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        email = customer.getEmail();
        phone = customer.getPhone();

        CustomerReadDto customerReadDto = new CustomerReadDto( id, firstName, lastName, email, phone );

        return customerReadDto;
    }
}

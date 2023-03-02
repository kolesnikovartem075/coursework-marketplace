package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.dto.CustomerCreateEditDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
public class CustomerCreateEditMapperImpl implements CustomerCreateEditMapper {

    @Override
    public Customer map(CustomerCreateEditDto customerCreateEditDto) {
        if ( customerCreateEditDto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.firstName( customerCreateEditDto.getFirstName() );
        customer.lastName( customerCreateEditDto.getLastName() );
        customer.email( customerCreateEditDto.getEmail() );
        customer.phone( customerCreateEditDto.getPhone() );

        return customer.build();
    }

    @Override
    public Customer map(CustomerCreateEditDto customerCreateEditDto, Customer entity) {
        if ( customerCreateEditDto == null ) {
            return entity;
        }

        entity.setFirstName( customerCreateEditDto.getFirstName() );
        entity.setLastName( customerCreateEditDto.getLastName() );
        entity.setEmail( customerCreateEditDto.getEmail() );
        entity.setPhone( customerCreateEditDto.getPhone() );

        return entity;
    }
}

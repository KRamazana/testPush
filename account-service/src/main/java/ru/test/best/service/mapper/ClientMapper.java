package ru.test.best.service.mapper;


import org.springframework.stereotype.Component;
import ru.test.best.dto.AccountDto;
import ru.test.best.entity.Client;

@Component
public class ClientMapper {

    public AccountDto convertClientToAccountDto(Client client) {
        return AccountDto.builder()
                .clientId(client.getClientId())
                .name(client.getName())
                .build();
    }
}

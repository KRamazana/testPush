package ru.test.best.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.test.best.dto.AccountDto;
import ru.test.best.entity.Client;
import ru.test.best.repository.ClientRepository;
import ru.test.best.service.mapper.ClientMapper;

import static ru.test.best.entity.enums.RoleEnum.SUPER;


@Service
@RequiredArgsConstructor
public class PointsService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public AccountDto addPoints(Long clientId, Long points) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Client not fount"));
        return addPoints(client, points);
    }

    @Transactional
    public AccountDto deductPoints(Long clientId, Long points) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Client not fount"));
        return deductPoints(client, points);
    }

    public Client getClient(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Client not fount"));
    }

    private AccountDto addPoints(Client client, Long points) {
        if (SUPER.name().equalsIgnoreCase(client.getRole().getRole())) {
            client.setPoints(client.getPoints() + points);
            return clientMapper.convertClientToAccountDto(client);
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Client has not Role Super");
        }
    }

    private AccountDto deductPoints(Client client, Long points) {
        long newPoints = client.getPoints() - points;
        if (newPoints >= 0) {
            client.setPoints(newPoints);
            return clientMapper.convertClientToAccountDto(client);
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not enough points to deduct");
        }
    }

}
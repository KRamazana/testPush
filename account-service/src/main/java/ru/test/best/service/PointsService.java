package ru.test.best.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.test.best.dto.AccountDto;
import ru.test.best.entity.Client;
import ru.test.best.repository.ClientRepository;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PointsService {
    private ClientRepository clientRepository;

    public PointsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public AccountDto addPoints(int clientId, int points) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            if (!client.getRole().getRole().contains("super")) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not access add point in this account");
            } else {
                client.setPoints(client.getPoints() + points);
                clientRepository.save(client);
                return convertClientToAccountDto(client);
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Client not fount");
        }
    }

    public AccountDto deductPoints(int clientId, int points) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            int newPoints = client.getPoints() - points;
            if (newPoints >= 0) {
                client.setPoints(newPoints);
                clientRepository.save(client);
                return convertClientToAccountDto(client);
            } else {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not enough points to deduct");
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Client not fount");
        }
    }

    public Client getClient(int clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent())
            return clientOptional.get();
        else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Client not found");
        }
    }

    private AccountDto convertClientToAccountDto(Client client) {
        return AccountDto.builder()
                .clientId(client.getClientId())
                .name(client.getName())
                .build();
    }
}

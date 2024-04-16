package ru.test.best.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.test.best.dto.AccountDto;
import ru.test.best.entity.Client;
import ru.test.best.service.PointsService;


@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointsController {
    private final PointsService pointsService;

    @PostMapping("/add")
    public AccountDto addPoints(@RequestParam Long clientId, @RequestParam Long points) {
        return pointsService.addPoints(clientId, points);
    }

    @PostMapping("/deduct")
    public AccountDto deductPoints(@RequestParam Long clientId, @RequestParam Long points) {
        return pointsService.deductPoints(clientId, points);
    }

    @GetMapping("/{clientId}")
    public Client getClient(@PathVariable Long clientId) {
        return pointsService.getClient(clientId);
    }
}
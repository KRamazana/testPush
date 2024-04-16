package ru.test.best.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class AccountDto implements Serializable {
    private Long clientId;
    private Long points;
    private String name;
    private String role;
}

package ru.test.best.entity;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "clients")
@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "clientId")
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    @NotNull
    @Column(name = "points")
    private int points;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "client_role_id", referencedColumnName = "id")
    private Role role;
}

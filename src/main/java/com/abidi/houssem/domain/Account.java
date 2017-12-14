package com.abidi.houssem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by houssemabidi on 12/12/17.
 */
@Entity
@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private  int id;

    @NotNull
    private String iban;

    private Double balance;

    @OneToMany(fetch = LAZY)
    private List<Operation> operations;

}

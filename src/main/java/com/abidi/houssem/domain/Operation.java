package com.abidi.houssem.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by houssemabidi on 12/12/17.
 */
@Entity
@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Operation {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Account account;

    private Date date;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private Double value;

}

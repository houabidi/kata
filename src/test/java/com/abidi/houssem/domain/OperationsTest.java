package com.abidi.houssem.domain;

import com.abidi.houssem.domain.repositories.Operations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by houssemabidi on 13/12/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OperationsTest {

    @Autowired
    private Operations operations;

    private Operation operation1;
    private Operation operation2;
    private Account account;

    @Autowired
    private TestEntityManager testEntityManager;


    @Before
    public void setUp() throws Exception {

        account = Account.builder()
                .iban("iban")
                .balance(0d)
                .build();

        operation1 = Operation.builder()
                .operationType(OperationType.DEBIT)
                .date(new Date())
                .account(account)
                .value(10d)
                .build();

        operation2 = Operation.builder()
                .operationType(OperationType.DEBIT)
                .date(new Date())
                .account(account)
                .value(20d)
                .build();

        testEntityManager.persist(account);
        testEntityManager.persist(operation1);
        testEntityManager.persist(operation2);

    }

    @Test
    public void testFindByAccountIban() {
        final List<Operation> byAccountIban = operations.findByAccountIban("iban");
        assertThat(byAccountIban).hasSize(2);
        assertThat(byAccountIban).isEqualTo(asList(operation1, operation2));
    }
}

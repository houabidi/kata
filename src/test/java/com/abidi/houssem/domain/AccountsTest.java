package com.abidi.houssem.domain;

import com.abidi.houssem.domain.repositories.Accounts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by houssemabidi on 13/12/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountsTest {

    @Autowired
    private Accounts accounts;

    @Test
    public void testFindByIban() {

        final Account account = Account.builder()
                .id(1)
                .iban("iban")
                .build();

        accounts.save(account);

        final Account byIban = accounts.findByIban("iban");

        assertThat(byIban).isNotNull();
        assertThat(byIban).isEqualTo(account);
    }
}

package com.abidi.houssem.application;

import com.abidi.houssem.domain.Account;
import com.abidi.houssem.domain.Operation;
import com.abidi.houssem.domain.exception.AccountNotFoundException;
import com.abidi.houssem.domain.exception.UnsupportedOperationException;
import com.abidi.houssem.domain.repositories.Accounts;
import com.abidi.houssem.domain.repositories.Operations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static com.abidi.houssem.domain.OperationType.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


/**
 * Created by houssemabidi on 13/12/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private Accounts accounts;

    @Mock
    private Operations operations;

    @Spy
    @InjectMocks
    private AccountService accountService;

    @Test
    public void testWithdrawMoney() {

        final Account account = Account.builder()
                .id(1)
                .balance(1500d)
                .iban("ibanaccount1")
                .build();

        final Account accountAfterWithdrawal = Account.builder()
                .id(1)
                .balance(500d)
                .iban("ibanaccount1")
                .build();

        final Operation operation = Operation.builder()
                .id(1)
                .value(1000d)
                .account(accountAfterWithdrawal)
                .operationType(DEBIT)
                .build();

        when(accounts.saveAndFlush(account)).thenReturn(accountAfterWithdrawal);
        when(operations.saveAndFlush(operation)).thenReturn(operation);

        final Account result = accountService.withdrawMoney(account, 1000d);

        assertThat(result).isEqualTo(accountAfterWithdrawal);

    }

    @Test
    public void testDepositMoney() {

        final Account account = Account.builder()
                .id(2)
                .balance(1500d)
                .iban("ibanaccount2")
                .build();

        final Account accountAfterWithdrawal = Account.builder()
                .id(2)
                .balance(2500d)
                .iban("ibanaccount2")
                .build();

        final Operation operation = Operation.builder()
                .id(2)
                .value(1000d)
                .account(accountAfterWithdrawal)
                .operationType(DEBIT)
                .build();

        when(accounts.saveAndFlush(account)).thenReturn(accountAfterWithdrawal);
        when(operations.saveAndFlush(operation)).thenReturn(operation);

        final Account result = accountService.depositMoney(account, 2500d);

        assertThat(result).isEqualTo(accountAfterWithdrawal);

    }

    @Test
    public void testUnsupportedOperationException() {

        when(accounts.findByIban("iban")).thenReturn(Account.builder().build());

        try {
            accountService.makeOperation("iban", null, 5d);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UnsupportedOperationException.class);
            assertThat(e.getMessage()).isEqualTo("Operation not supported");
        }
    }

    @Test
    public void testAccountNotFoundException() {

        when(accounts.findByIban("iban1")).thenReturn(null);

        try {
            accountService.makeOperation("iban1", null, 5d);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(AccountNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo("Account iban1 is not found");
        }
    }

}

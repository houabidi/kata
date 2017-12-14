package com.abidi.houssem.application;

import com.abidi.houssem.domain.Account;
import com.abidi.houssem.domain.Operation;
import com.abidi.houssem.domain.OperationType;
import com.abidi.houssem.domain.exception.AccountNotFoundException;
import com.abidi.houssem.domain.exception.UnsupportedOperationException;
import com.abidi.houssem.domain.repositories.Accounts;
import com.abidi.houssem.domain.repositories.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.abidi.houssem.domain.OperationType.CREDIT;
import static com.abidi.houssem.domain.OperationType.DEBIT;

/**
 * Created by houssemabidi on 12/12/17.
 */
@Service
@Transactional
public class AccountService {

    private final Accounts accounts;
    private final Operations operations;

    @Autowired
    public AccountService(Accounts accounts, Operations operations) {
        this.accounts = accounts;
        this.operations = operations;
    }

    /**
     * Method to make an operation on an account
     *
     * @param iban          the account's iban
     * @param operationType the type of the operation
     * @param moneySum      the operation money sum
     * @return the account
     */
    public Account makeOperation(String iban, OperationType operationType, Double moneySum) {
        Account account = accounts.findByIban(iban);
        if (account != null) {
            if (CREDIT.equals(operationType)) {
                return depositMoney(account, moneySum);
            } else if (DEBIT.equals(operationType)) {
                return withdrawMoney(account, moneySum);
            } else {
                throw new UnsupportedOperationException("Operation not supported");
            }
        } else {
            throw new AccountNotFoundException("Account " + iban + " is not found");
        }
    }

    /**
     * Method to withdraw money from a given account
     *
     * @param account  the account
     * @param moneySum the sum to withdraw
     * @return the saved account
     */
    Account withdrawMoney(Account account, Double moneySum) {
        double newBalance = account.getBalance() - moneySum;
        account.setBalance(newBalance);
        final Operation operation = Operation.builder()
                .operationType(CREDIT)
                .account(account)
                .value(moneySum)
                .date(new Date())
                .build();
        operations.saveAndFlush(operation);
        return accounts.saveAndFlush(account);
    }

    /**
     * Method to deposit money from a given account
     *
     * @param account  the account
     * @param moneySum the sum to deposit
     * @return the saved account
     */
    Account depositMoney(Account account, Double moneySum) {
        double newBalance = account.getBalance() + moneySum;
        account.setBalance(newBalance);
        final Operation operation = Operation.builder()
                .operationType(DEBIT)
                .account(account)
                .value(moneySum)
                .date(new Date())
                .build();
        operations.save(operation);
        return accounts.saveAndFlush(account);
    }
}

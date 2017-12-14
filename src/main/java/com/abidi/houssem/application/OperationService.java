package com.abidi.houssem.application;

import com.abidi.houssem.domain.Account;
import com.abidi.houssem.domain.Operation;
import com.abidi.houssem.domain.repositories.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by houssemabidi on 12/12/17.
 */
@Service
@Transactional
public class OperationService {

    private final Operations operations;

    @Autowired
    public OperationService(Operations operations) {
        this.operations = operations;
    }

    /**
     * Method to return the operations of a given account
     *
     * @param iban the iban
     * @return the list of operations
     */
    public List<Operation> listByAccount(String iban) {
        return operations.findByAccountIban(iban);
    }
}

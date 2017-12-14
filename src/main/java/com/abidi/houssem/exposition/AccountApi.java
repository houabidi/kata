package com.abidi.houssem.exposition;

import com.abidi.houssem.application.AccountService;
import com.abidi.houssem.application.OperationService;
import com.abidi.houssem.domain.Account;
import com.abidi.houssem.domain.Operation;
import com.abidi.houssem.domain.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by houssemabidi on 12/12/17.
 */
@RestController
public class AccountApi {

    private final AccountService accountService;

    private final OperationService operationService;

    @Autowired
    public AccountApi(AccountService accountService, OperationService operationService) {
        this.accountService = accountService;
        this.operationService = operationService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/operations/{iban}")
    public List<Operation> getOperations(@PathVariable String iban) {
        return operationService.listByAccount(iban);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/makeOperation/{iban}/{operationType}")
    public Account makeOperation(@PathVariable String iban,
                                 @PathVariable OperationType operationType,
                                 @RequestBody Double value) {
        return accountService.makeOperation(iban, operationType, value);
    }
}

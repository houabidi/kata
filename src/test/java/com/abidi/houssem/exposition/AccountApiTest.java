package com.abidi.houssem.exposition;

import com.abidi.houssem.application.AccountService;
import com.abidi.houssem.application.OperationService;
import com.abidi.houssem.domain.Account;
import com.abidi.houssem.domain.Operation;
import com.abidi.houssem.domain.OperationType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static com.abidi.houssem.domain.OperationType.DEBIT;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by houssemabidi on 13/12/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AccountApi.class)
public class AccountApiTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @MockBean
    private OperationService operationService;

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void testMakeWithdrawalOperation() throws Exception {

        final Account account = Account.builder()
                .id(2)
                .iban("iban2")
                .balance(1200d)
                .build();

        when(accountService.makeOperation("iban2", DEBIT, 130d)).thenReturn(account);

        mockMvc.perform(post("/makeOperation/iban2/DEBIT")
                .content("130")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(
                                "{\"id\":2,\"iban\":\"iban2\",\"balance\":1200.0,\"operations\":null}"
                        )
                );
    }

    @Test
    public void testMakeDepositOperation() throws Exception {

        final Account account = Account.builder()
                .id(1)
                .iban("iban1")
                .balance(1000d)
                .build();

        when(accountService.makeOperation("iban1", OperationType.CREDIT, 100d)).thenReturn(account);

        mockMvc.perform(post("/makeOperation/iban1/CREDIT")
                .content("100")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(
                                "{\"id\":1,\"iban\":\"iban1\",\"balance\":1000.0,\"operations\":null}"
                        )
                );

    }

    @Test
    public void testGetOperations() throws Exception {

        final Operation operation1 = Operation.builder()
                .id(1)
                .operationType(OperationType.DEBIT)
                .account(Account.builder().id(1).build())
                .date(new Date(2000,1,1))
                .build();

        final Operation operation2 = Operation.builder()
                .id(2)
                .operationType(OperationType.DEBIT)
                .account(Account.builder().id(2).build())
                .date(new Date(2018,1,1))
                .build();

        when(operationService.listByAccount("iban")).thenReturn(asList(operation1,operation2));

        mockMvc.perform(get("/operations/iban")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("[{\"id\":1,\"account\":{\"id\":1,\"iban\":null,\"balance\":null,\"operations\":null},\"date\":60907590000000,\"operationType\":\"DEBIT\",\"value\":null},{\"id\":2,\"account\":{\"id\":2,\"iban\":null,\"balance\":null,\"operations\":null},\"date\":61475583600000,\"operationType\":\"DEBIT\",\"value\":null}]")
                );


    }
}

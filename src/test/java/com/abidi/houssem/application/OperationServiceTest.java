package com.abidi.houssem.application;

import com.abidi.houssem.domain.Operation;
import com.abidi.houssem.domain.repositories.Operations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


/**
 * Created by houssemabidi on 13/12/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTest {

    @Mock
    private Operations operations;

    @Spy
    @InjectMocks
    private OperationService operationService;

    @Test
    public void testListByAccount() {
        final List<Operation> operationList = singletonList(Operation.builder().id(1).build());
        when(operations.findByAccountIban("iban")).thenReturn(operationList);
        final List<Operation> result = operationService.listByAccount("iban");
        assertThat(result).isEqualTo(operationList);
    }
}

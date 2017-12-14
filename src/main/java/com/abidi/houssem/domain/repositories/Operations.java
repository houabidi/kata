package com.abidi.houssem.domain.repositories;

import com.abidi.houssem.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by houssemabidi on 12/12/17.
 */
@Repository
public interface Operations extends JpaRepository<Operation, String> {

    List<Operation> findByAccountIban(String iban);
}

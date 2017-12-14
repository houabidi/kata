package com.abidi.houssem.domain.repositories;

import com.abidi.houssem.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by houssemabidi on 12/12/17.
 */
@Repository
public interface Accounts extends JpaRepository<Account, String> {

    Account findByIban(String iban);
}

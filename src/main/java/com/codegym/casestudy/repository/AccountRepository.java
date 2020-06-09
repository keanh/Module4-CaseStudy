package com.codegym.casestudy.repository;

import com.codegym.casestudy.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account,Long> {
    Account findAccountByUsername(String username);
    Account findAccountByUsernameAndPassword(String username,String password);
}

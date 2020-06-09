package com.codegym.casestudy.service;

import com.codegym.casestudy.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService{
    List<Account> findAll();
    Optional<Account> findById(Long id);
    void save(Account account);
    void remove(Long id);
    Account findAccountByUsername(String username);
    Account findAccountByUsernameAndPassword(String username,String password);
}

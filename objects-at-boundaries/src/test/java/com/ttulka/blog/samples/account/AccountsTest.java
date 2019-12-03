package com.ttulka.blog.samples.account;

import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class AccountsTest {

    @Test
    void registered_account_with_correct_password_can_log_in(@Autowired AccountEntries entries) {
        Accounts accounts = new PersistentAccounts(entries);

        String username = UUID.randomUUID().toString();
        accounts.register(username, "pwd1", "test@example.com");

        Account account = accounts.login(username, "pwd1");
        assertThat(account).isNotNull();
    }

    @Test
    void once_registered_a_username_cannot_be_registered_again(@Autowired AccountEntries entries) {
        Accounts accounts = new PersistentAccounts(entries);

        String username = UUID.randomUUID().toString();
        accounts.register(username, "test@example.com", "pwd1");

        assertThrows(AccountException.class, () ->
                accounts.register(username, "test@example.com", "pwd1"));
    }

    @Test
    void non_existing_account_cannot_log_in(@Autowired AccountEntries entries) {
        Accounts accounts = new PersistentAccounts(entries);

        assertThrows(AccountException.class, () ->
                accounts.login("this user does not exist", "password"));
    }

    @Test
    void existing_account_with_wrong_password_cannot_log_in(@Autowired AccountEntries entries) {
        Accounts accounts = new PersistentAccounts(entries);

        String username = UUID.randomUUID().toString();
        accounts.register(username, "test@example.com", "pwd1");

        assertThrows(AccountException.class, () ->
                accounts.login(username, "wrong password"));
    }
}

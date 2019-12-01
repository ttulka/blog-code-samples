package com.ttulka.blog.samples.account;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserAccountTest {

    @Test
    void returns_username(@Autowired UserAccountEntries entries) {
        Account account = new UserAccount("test", "test@example.com", "pwd1", entries);

        assertThat(account.username()).isEqualTo("test");
    }

    @Test
    void invalid_username_raises_an_error(@Autowired UserAccountEntries entries) {
        assertThrows(Exception.class, () ->
                new UserAccount(null, "test@example.com", "pwd1", entries));
    }

    @Test
    void invalid_email_raises_an_error(@Autowired UserAccountEntries entries) {
        assertThrows(Exception.class, () ->
                new UserAccount("test", null, "pwd1", entries));
    }

    @Test
    void invalid_password_raises_an_error(@Autowired UserAccountEntries entries) {
        assertThrows(Exception.class, () ->
                new UserAccount("test", "test@example.com", null, entries));
    }

    @Test
    void can_login_with_a_valid_password(@Autowired UserAccountEntries entries) {
        Account account = new UserAccount("test", "test@example.com", "pwd1", entries);

        assertThat(account.canLogin("pwd1")).isTrue();
    }

    @Test
    void cannot_login_with_an_invalid_password(@Autowired UserAccountEntries entries) {
        Account account = new UserAccount("test", "test@example.com", "pwd1", entries);

        assertThat(account.canLogin("xxx")).isFalse();
    }

    @Test
    void valid_password_is_changed(@Autowired UserAccountEntries entries) {
        Account account = new UserAccount("test", "test@example.com", "pwd1", entries);
        account.changePassword("updated");

        assertThat(account.canLogin("pwd1")).isFalse();
        assertThat(account.canLogin("updated")).isTrue();
    }
}

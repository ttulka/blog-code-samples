package com.ttulka.blog.samples.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
class AccountController {

    private final Accounts accounts;

    @GetMapping
    public LoginDTO login(String username, String password) {
        Account account = accounts.login(username, password);
        return new LoginDTO(account.username());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody ToRegisterDTO toRegister) {
        accounts.register(toRegister.getUsername(), toRegister.getPassword(), toRegister.getEmail());
    }

    @Value
    static class LoginDTO {
        private final String username;
    }

    @Value
    static class ToRegisterDTO {
        private final String username;
        private final String password;
        private final String email;
    }
}

package com.ttulka.blog.samples.account;

import java.util.Map;

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
    public Map<String, String> login(String username, String password) {
        Account account = accounts.login(username, password);
        return Map.of("username", account.username());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody Map<String, String> toRegister) {
        accounts.register(toRegister.get("username"), toRegister.get("password"), toRegister.get("email"));
    }
}

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
class AccountController {

    private final Accounts accounts;

    @GetMapping
    public Account login(String username, String password) {
        return accounts.login(username, password);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody Map<String, String> data) {
        accounts.register(data.get("username"), data.get("password"), data.get("email"));
    }
}

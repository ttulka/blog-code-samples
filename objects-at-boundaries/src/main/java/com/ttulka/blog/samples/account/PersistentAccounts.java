package com.ttulka.blog.samples.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class PersistentAccounts implements Accounts {

    private final AccountEntries entries;

    @Override
    public Account register(@NonNull String username, @NonNull String password, @NonNull String email) {
        if (entries.findByUsername(username).isPresent()) {
            throw new AccountException("Username already exists: " + username);
        }
        Account account = new PersistentAccount(username, email, password, entries);
        account.register();
        account.login();
        return account;
    }

    @Override
    public Account login(@NonNull String username, @NonNull String password) {
        Account account = registeredAccountByUsername(username);
        if (!account.canLogin(password)) {
            throw new AccountException("Wrong password.");
        }
        account.login();
        return account;
    }

    private Account registeredAccountByUsername(String username) {
        return entries.findByUsername(username)
                .map(entry -> new PersistentAccount(
                        entry.id, entry.username, entry.email, entry.encryptedPassword, entry.salt, entry.lastLoggedIn,
                        entries))
                .orElseThrow(() -> new AccountException("Not found: " + username));
    }
}

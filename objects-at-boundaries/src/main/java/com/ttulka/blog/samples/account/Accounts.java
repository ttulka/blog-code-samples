package com.ttulka.blog.samples.account;

/**
 * Service for the Account.
 */
public interface Accounts {

    /**
     * Registers a new Account, if the username doesn't exist yet and logs-in the user.
     *
     * @param username the username
     * @param password the clear text password
     * @param email    the email address of the user
     * @return the newly registered Account
     * @throws AccountException if any errors occur
     */
    Account register(String username, String password, String email);

    /**
     * Logs in the user, if the username exists and the password is correct. Updates the last logged date
     *
     * @param username the username
     * @param password the clear text password
     * @return the logged-in account
     * @throws Exception if any errors occur
     */
    Account login(String username, String password);
}

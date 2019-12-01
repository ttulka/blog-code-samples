package com.ttulka.blog.samples.account;

/**
 * Aggregate for the Account.
 */
public interface Account {

    /**
     * Returns the Account username.
     *
     * @return the username
     */
    String username();

    /**
     * Registers the Account.
     *
     * @throws AccountException when registering an already registered Account
     */
    void register();

    /**
     * Unregisters the Account.
     */
    void unregister();

    /**
     * Returns whether login can be performed with the provided password.
     *
     * @return true if login can be performed with the provided password, otherwise false
     */
    boolean canLogin(String password);

    /**
     * Performs login.
     */
    void login();

    /**
     * Changes the Account password.
     *
     * @param newPassword the new password of the Account
     */
    void changePassword(String newPassword);
}

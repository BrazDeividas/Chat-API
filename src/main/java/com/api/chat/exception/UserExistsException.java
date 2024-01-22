package com.api.chat.exception;

public class UserExistsException extends RuntimeException {
        public UserExistsException(String username) {
            super("An user with the username " + username + " already exists");
        }
}

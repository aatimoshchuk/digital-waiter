package nsu.sber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public sealed class DigitalWaiterException extends RuntimeException {

    private DigitalWaiterException(String message) {
        super(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class IncorrectPasswordException extends DigitalWaiterException {
        public IncorrectPasswordException() {
            super("Incorrect password");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class TableAuthWithThisIdNotFoundException extends DigitalWaiterException {
        public TableAuthWithThisIdNotFoundException(int id) {
            super("Authorization for table with id %s was not found".formatted(id));
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class TableAuthWithThisLoginNotFoundException extends DigitalWaiterException {
        public TableAuthWithThisLoginNotFoundException(String login) {
            super("Authorization for table with login %s was not found".formatted(login));
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class OrganizationNotFoundException extends DigitalWaiterException {
        public OrganizationNotFoundException(int id) {
            super("Organization with id %s was not found".formatted(id));
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class TerminalGroupNotFoundException extends DigitalWaiterException {
        public TerminalGroupNotFoundException(int id) {
            super("Terminal group with id %s was not found".formatted(id));
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class RestaurantTableNotFoundException extends DigitalWaiterException {
        public RestaurantTableNotFoundException(int id) {
            super("Restaurant table with id %s was not found".formatted(id));
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static final class InvalidTokenException extends DigitalWaiterException {
        public InvalidTokenException() {
            super("Invalid or expired token");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class ExternalMenuNotFoundException extends DigitalWaiterException {
        public ExternalMenuNotFoundException(String id) {
            super("External menu with id %s was not found".formatted(id));
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class DishNotFoundException extends DigitalWaiterException {
        public DishNotFoundException(String id) {
            super("Dish with id %s was not found".formatted(id));
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static final class InterruptedOrderCreationException extends DigitalWaiterException {
        public InterruptedOrderCreationException() {
            super("Order creation was interrupted");
        }
    }

    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    public static final class OrderCreationTimedOutException extends DigitalWaiterException {
        public OrderCreationTimedOutException() {
            super("Order creation timed out");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class OrderCreationException extends DigitalWaiterException {
        public OrderCreationException(String message) {
            super("Order creation failed: %s".formatted(message));
        }
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public static final class PosOperationException extends DigitalWaiterException {
        public PosOperationException(String message) {
            super("POS operation failed: %s".formatted(message));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class EmptyCartException extends DigitalWaiterException {
        public EmptyCartException() {
            super("Unable to create order: cart is empty");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class UserWithThisLoginNotFoundException extends DigitalWaiterException {
        public UserWithThisLoginNotFoundException() {
            super("User with this login was not found");
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static final class ApiKeyEncryptionException extends DigitalWaiterException {
        public ApiKeyEncryptionException() {
            super("Failed to encrypt API key");
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static final class ApiKeyDecryptionException extends DigitalWaiterException {
        public ApiKeyDecryptionException() {
            super("Failed to decrypt API key");
        }
    }

    public static final class UserHasNoRestaurantTableException extends DigitalWaiterException {
        public UserHasNoRestaurantTableException() {
            super("User has no restaurant table assigned");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class InvalidUserRoleException extends DigitalWaiterException {
        public InvalidUserRoleException() {
            super("User role is not allowed to perform this action");
        }
    }
}

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
    public static final class OrganizationWithThisIdNotFoundException extends DigitalWaiterException {
        public OrganizationWithThisIdNotFoundException(int id) {
            super("Organization with id %s was not found".formatted(id));
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class NoOrganizationForRestaurantTableException extends DigitalWaiterException {
        public NoOrganizationForRestaurantTableException() {
            super("Organization associated with provided restaurant table was not found");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class TerminalGroupWithThisIdNotFoundException extends DigitalWaiterException {
        public TerminalGroupWithThisIdNotFoundException(int id) {
            super("Terminal group with id %s was not found".formatted(id));
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class NoTerminalGroupForRestaurantTableException extends DigitalWaiterException {
        public NoTerminalGroupForRestaurantTableException() {
            super("Terminal group associated with provided restaurant table was not found");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class RestaurantTableWithThisIdNotFoundException extends DigitalWaiterException {
        public RestaurantTableWithThisIdNotFoundException(int id) {
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

    @ResponseStatus(HttpStatus.CONFLICT)
    public static final class OrganizationAlreadyExistException extends DigitalWaiterException {
        public OrganizationAlreadyExistException() {
            super("Organization with the given posOrganizationId already exists");
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static final class TerminalGroupAlreadyExistException extends DigitalWaiterException {
        public TerminalGroupAlreadyExistException() {
            super("Terminal group with the given posTerminalGroupId associated with the given organization already " +
                    "exist");
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static final class OrganizationHasDependenciesException extends DigitalWaiterException {
        public OrganizationHasDependenciesException() {
            super("Cannot delete organization due to existing dependencies");
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static final class TerminalGroupHasDependenciesException extends DigitalWaiterException {
        public TerminalGroupHasDependenciesException() {
            super("Cannot delete terminal group due to existing dependencies");
        }
    }
}

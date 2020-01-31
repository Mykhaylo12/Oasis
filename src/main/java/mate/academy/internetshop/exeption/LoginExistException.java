package mate.academy.internetshop.exeption;

public class LoginExistException extends DataProcessingException {
    public LoginExistException(String message) {
        super(message);
    }
}

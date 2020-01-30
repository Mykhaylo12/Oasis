package mate.academy.internetshop.exeption;

public class LoginExistExeption extends DataProcessingException {
    public LoginExistExeption(String message) {
        super(message);
    }
}

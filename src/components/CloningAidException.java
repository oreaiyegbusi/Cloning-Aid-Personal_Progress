package components;

public class CloningAidException extends Exception{
    public CloningAidException() {
        super("Cloning Aid Exception");
    }

    public CloningAidException(String message) {
        super("Cloning Aid Exception: " + message);
    }

}

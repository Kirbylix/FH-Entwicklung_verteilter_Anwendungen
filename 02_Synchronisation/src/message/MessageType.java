package message;

public enum MessageType {
    REQUEST("Anfrage"),
    RESPONSE("Antwort"),
    ERROR("Fehler :)");

    private String value;

    private MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.ded.BTS.Exceptions;

public class InvalidEnumException extends RuntimeException {

    private final Class<? extends Enum<?>> enumType;
    private final String value;

    public InvalidEnumException(
            Class<? extends Enum<?>> enumType,
            String value) {

        super("Invalid value '%s' for enum %s"
                .formatted(value,
                        enumType.getSimpleName()));

        this.enumType = enumType;
        this.value = value;
    }

    public Class<? extends Enum<?>> getEnumType() {
        return enumType;
    }

    public String getValue() {
        return value;
    }
}

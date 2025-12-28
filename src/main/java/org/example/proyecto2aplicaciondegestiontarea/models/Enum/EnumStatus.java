package org.example.proyecto2aplicaciondegestiontarea.models.Enum;

public enum EnumStatus {
    PENDING("Pendiente"),
    IN_PROGRESS("En progreso"),
    COMPLETED("Completado");

    private final String labe;

    EnumStatus(String labe) {
        this.labe = labe;
    }

    public String getLabe() {
        return labe;
    }
}

package org.example.proyecto2aplicaciondegestiontarea.models.Enum;

public enum EnumStatus {
    PENDING("Pendiente"),
    IN_PROGRESS("En progreso"),
    COMPLETED("Completado");

    private final String label;

    EnumStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

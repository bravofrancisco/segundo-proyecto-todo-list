package org.example.proyecto2aplicaciondegestiontarea.models.Enum;

public enum EnumPriority {
    LOW("Baja"),
    MEDIUM("Media"),
    HIGH("Alta");

    private final  String label;

    EnumPriority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}


package com.pao.laboratory03.enums;

public enum Priority {
    LOW(1, "green") {
        @Override
        public String getEmoji() { return "🟢"; }
    },
    MEDIUM(2, "yellow") {
        @Override
        public String getEmoji() { return "🟡"; }
    },
    HIGH(3, "orange") {
        @Override
        public String getEmoji() { return "🟠"; }
    },
    CRITICAL(4, "red") {
        @Override
        public String getEmoji() { return "🔴"; }
    };

    private final int level;
    private final String color;

    // Constructorul este implicit privat
    Priority(int level, String color) {
        this.level = level;
        this.color = color;
    }

    public int getLevel() { return level; }
    public String getColor() { return color; }

    // Fiecare constantă trebuie să implementeze această metodă
    public abstract String getEmoji();
}
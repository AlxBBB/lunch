package topjava.graduation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Menu extends AbstractBaseEntity {
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private LocalDate date;
}

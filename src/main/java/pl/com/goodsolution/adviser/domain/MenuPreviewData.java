package pl.com.goodsolution.adviser.domain;

import java.time.LocalDate;
import java.util.Map;

public class MenuPreviewData {
    private LocalDate date;
    private Map<String, String> meals;

    public MenuPreviewData(LocalDate date, Map<String, String> meals) {
        this.date = date;
        this.meals = meals;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<String, String> getMeals() {
        return meals;
    }
}

package dev.luanfernandes.lucene.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ShoppingList {
    private String name;
    private LocalDate date;
    private List<String> items;
    private String fileName;
}
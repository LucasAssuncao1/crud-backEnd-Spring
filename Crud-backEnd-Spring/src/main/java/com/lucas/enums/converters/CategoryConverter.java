package com.lucas.enums.converters;

import java.util.stream.Stream;

import com.lucas.enums.Category;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category category) {
        if (category == null) {
            return null;
        }
        return category.getValue();
    }

    @Override
    public Category convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(Category.values())
                .filter(category -> category.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }

}

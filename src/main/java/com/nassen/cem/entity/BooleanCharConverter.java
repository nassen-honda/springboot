package com.nassen.cem.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Boolean の列の変換（自動生成 / auto-generated）：DB の型は CHAR(1)。'1'：True、'0'：False、null：null。
 *
 * <p>手動編集は再生成で失われます。</p>
 */
@Converter
public class BooleanCharConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return value == null ? null : value ? "1" : "0";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return value == null || value.isBlank() ? null : "1".equals(value.trim());
    }
}

package com.cotton.model.base;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConvert implements AttributeConverter<Boolean, String>{

	public Boolean convertToEntityAttribute(String dbData) {
		
		return "Y".equals(dbData);
		
	}
	
	public String convertToDatabaseColumn(Boolean attribute) {
		
		if (Boolean.TRUE.equals(attribute)) {
            return "Y";
        } else {
            return "N";
        }
		
	}

}

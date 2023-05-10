package com.springboot.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Esta @ le indica a (SB) que responda un (HttpStatus) específico cada vez que la (Exception) sea arrojada por el (Controller)
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private long fieldValue;

    //dinámicamente se reemplazarán los valores como en el ejemplo:
    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue)); // (Post) not found with (id):(1)
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    //los campos fueron inicializados en el constructor, entonces no necesitan (setter), solo (getter)

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }
}

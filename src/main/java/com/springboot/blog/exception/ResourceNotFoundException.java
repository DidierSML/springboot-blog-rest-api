package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Esta @ le indica a (SB) que responda un (HttpStatus) específico cada vez que la (Exception) sea arrojada por él (Controller)
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private long fieldValue;

    //Dinámicamente, se reemplazarán los valores como en el ejemplo:
    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {

        // (Post) not Found with (id):(1)
        super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    //Los campos fueron inicializados en el constructor, entonces no necesitan (setter), solo (getter)

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

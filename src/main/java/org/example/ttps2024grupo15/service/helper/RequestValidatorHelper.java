package org.example.ttps2024grupo15.service.helper;

public class RequestValidatorHelper {


    public static void validateID(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
    }

    public static void validateDoubleInputParameter(Double param, String message){
        if(param == null || param < 0){
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateStringInputParameter(String param, String message){
        if(param == null || param.isEmpty()){
            throw new IllegalArgumentException(message);
        }
    }
}
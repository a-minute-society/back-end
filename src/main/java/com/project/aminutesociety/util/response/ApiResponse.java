package com.project.aminutesociety.util.response;

import com.project.aminutesociety.usercategory.entity.UserCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private static final int OK = 200;
    private static final int BAD_REQUEST = 400;
    private static final int NOT_FOUND = 404;

    private int status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> createSuccessWithData(T data, String message) {
        return new ApiResponse<>(OK, data, message);
    }

    public static <T> ApiResponse<T> createSuccessWithoutData(int status, String message) {
        return new ApiResponse<>(status, null, message);
    }

    public static <T> ApiResponse<T> createFailWithoutData(int status, String message) {
        return new ApiResponse<>(status, null, message);
    }

    public static <T> ApiResponse<T> loginSuccessWithoutData(T data, String message){
        return new ApiResponse<>(OK, data, message);
    }

    public static <T> ApiResponse<T> loginFailWithoutData(int status, String message) {
        return new ApiResponse<>(status, null, message);
    }

    public static<T> ApiResponse<T> checkUserIdFailWithoutData(int status, String message) {
        return new ApiResponse<>(status, null, message);
    }

    public static<T> ApiResponse<T> checkUserIdSuccessWithData(T data, String message) {
        return new ApiResponse<>(OK, data, message);
    }

    public static<T> ApiResponse<T> readCategoriesSuccessWithData(T data, String message) {
        return new ApiResponse<>(OK, data, message);
    }

    public static<T> ApiResponse<T> readCategoriesFailWithoutData(int status, String message) {
        return new ApiResponse<>(status, null, message);
    }

    public static <T> ApiResponse<T> createFailWithData(int status, BindingResult bindingResult) {
        List<String> errorMessages = bindingResult.getAllErrors().stream()
                .map(ObjectError::getObjectName)
                .collect(Collectors.toList());

        String combinedMessage = String.join("; ", errorMessages);

        return new ApiResponse<>(status, null, combinedMessage);
    }

    public ApiResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

}

package com.tigasatutiga.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Standard API response wrapper for all endpoints.
 * @param <T> Type of the data payload
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseModel<T> {

    private boolean success;
    private String message;
    private T data;

    // Static helper for success responses
    public static <T> ApiResponseModel<T> success(T data, String message) {
        return new ApiResponseModel<>(true, message, data);
    }

    // Static helper for failure responses
    public static <T> ApiResponseModel<T> failure(String message) {
        return new ApiResponseModel<>(false, message, null);
    }

    // Optional: static helper for failure with data (rare, but sometimes useful)
    public static <T> ApiResponseModel<T> failure(String message, T data) {
        return new ApiResponseModel<>(false, message, data);
    }
}

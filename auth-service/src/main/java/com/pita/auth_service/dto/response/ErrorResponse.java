package com.pita.auth_service.dto.response;

public record ErrorResponse(String code, String error, String details) {
}

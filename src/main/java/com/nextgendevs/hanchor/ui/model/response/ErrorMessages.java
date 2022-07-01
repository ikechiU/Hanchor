package com.nextgendevs.hanchor.ui.model.response;

public enum ErrorMessages {

    RECORD_ALREADY_EXISTS("Record already exists."),
    EMAIL_ALREADY_EXISTS("Email already exists."),
    RESERVED_EMAIL("Domain email."),
    MISMATCH_RECORD("Mismatch detected."),
    ACCESS_DENIED("Only an admin can perform this operation."),
    INVALID_EMAIL("Invalid email."),
    MISSING_REQUIRED_FIELD("Missing required field."),
    NO_RECORD_FOUND("No record found."),
    ERROR_UPLOADING_IMAGE("Error uploading image."),
    ERROR_DELETING_IMAGE("Error deleting image."),
    ERROR_CONVERTING_IMAGE("Image file error."),
    DELETE_ERROR("Image could not be deleted, try again or contact admin.");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}

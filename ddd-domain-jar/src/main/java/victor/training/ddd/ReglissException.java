package victor.training.ddd;

import java.util.Arrays;

/**
 * A business error condition that can be translated to be displayed to the end user.
 */
@SuppressWarnings("serial")
public class ReglissException extends RuntimeException {

	public enum ErrorCode {
	    GENERAL,
	    LIST_REFERENCE_ALREADY_IN_USE,     
	    LIST_NAME_ALREADY_IN_USE, 
	    LIST_SHORTNAME_ALREADY_IN_USE, 
	    RECORD_EXTERNAL_REFERENCE_ALREADY_IN_USE,
	    FILE_NOT_IN_REQUEST, 
	    FILE_PROCESS_ERROR, 
	    SEARCH_MULTIPLE_WILDCARDS, 
		
	    MAPPING_FILE_NOT_DEFINED, 
		MAPPING_FILE_PARSE_ERROR,
		MAPPING_FILE_ILLEGAL_EXTENSION,
		MAPPING_FILE_EMPTY,
		MAPPING_FILE_DUPLICATE_FIELDS, 
	    
		
		IMPORT_FILE_PARSE_ERROR, 
		IMPORT_FILE_HEADER_DIFFERENT, 
		IMPORT_FILE_HEADER_LENGTH_DIFFERENT_FROM_MAPPING_FILE_LENGTH, 

		IMPORT_ILLEGAL_VALUE,
		IMPORT_ROW_LENGTH_DIFFERENT_FROM_HEADER_LENGTH,
		IMPORT_RECORD_MISSING_TYPE,
		IMPORT_RECORD_MISSING_NAME, 
		IMPORT_RECORD_MISSING_EXTERNAL_REFERENCE, 
		IMPORT_RECORD_MISSING_START_DATE, 
		IMPORT_RECORD_INVALID_START_DATE_FORMAT, 
		IMPORT_RECORD_INVALID_BIRTH_DATE_FORMAT, 
		IMPORT_RECORD_INVALID_DEATH_DATE_FORMAT,
		IMPORT_RECORD_MISSING_SANCTION_TYPE, 
		
		IMPORT_ADDRESS_WITHOUT_CITY_COUNTRY, 
		IMPORT_ADDRESS_STREET_WITHOUT_CITY, 
		IMPORT_ALIAS_WITHOUT_REFERENCE, 
		IMPORT_ALIAS_WITHOUT_NAME, 
		IMPORT_PROGRAM_WITHOUT_LABEL,
		IMPORT_DOCUMENT_WITHOUT_TYPE, 
		IMPORT_DOCUMENT_WITHOUT_NUMBER, 
		IMPORT_BIOGRAPHY_EMPTY, 
		IMPORT_NATIONALITY_WITHOUT_COUNTRY, 
		IMPORT_RECORD_WITHOUT_PROGRAMS,		
		IMPORT_PERSON_MISSING_LAST_NAME, 
		
		GENERATE_FILE_ERROR, 
		SUBSCRIPTION_DUPLICATE_CFT_FLUX_NAME,
		
		NOT_FOUND,
		
		SEARCH_REFOG_USER_NOT_FOUND,
		CREATE_USER_UID_ALREADY_USED,
		CREATE_USER_REFOG_COUNTRY_NOT_IN_REGLISS, 
		SUPPORT_MOA_CANNOT_EDIT_OWN_PROFILE, 
		LOST_LOCK,
		
		NO_PERMISSION_FOR_LIST, 
		XSD_VALIDATION_FAILED, 
		AUTHENTICATION_HIGH_LEVEL_REQUIRED, 
		
		DJ_IMPORT_VALIDATION_ERROR, 
		NOT_ALLOWED_BY_PROFILE
	}

	private final ErrorCode errorCode;
	private final String[] parameters;
	
	public ReglissException() {
	    this((String)null);
	}
	
	public ReglissException(String message) {
	    this(message, null, ErrorCode.GENERAL);
	}
	
	public ReglissException(Throwable cause) {
        this(cause.toString(), cause, ErrorCode.GENERAL);
    }
	
	public ReglissException(Throwable cause, String message) {
        this(message, cause, ErrorCode.GENERAL);
    }

	public ReglissException(ErrorCode errorCode, String... parameters) {
		this(null, null, errorCode, parameters);
	}

	public ReglissException(String exceptionMessage, ErrorCode errorCode, String... parameters) {
		this(exceptionMessage, null, errorCode, parameters);
	}

	public ReglissException(Throwable cause, ErrorCode errorCode, String... parameters) {
		this(null, cause, errorCode, parameters);
	}

	public ReglissException(String exceptionMessage, Throwable cause, ErrorCode errorCode, String... parameters) {
		super((exceptionMessage != null ? exceptionMessage : "") + (errorCode!=ErrorCode.GENERAL?errorCode.name():"") + (parameters.length>0?" " + Arrays.toString(parameters):""), cause);
		this.errorCode = errorCode;
		this.parameters = parameters;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String[] getParameters() {
		return parameters;
	}
}

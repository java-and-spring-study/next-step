package enums;


public enum HttpStatus {
	OK(200, "Ok"),
	CREATED(201, "Created"),
	REDIRECT(302, "Found");

	private final int code;
	private final String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	HttpStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
}

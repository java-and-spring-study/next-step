package webserver;

import java.util.Arrays;

public enum HttpMethod {
	POST("POST"),
	GET("GET");

	private final String description;

	HttpMethod(String description) {
		this.description = description;
	}

	public static HttpMethod findBy(String method) {
		if (method == null) {
			return null;
		}
		return Arrays.stream(HttpMethod.values())
			.filter((httpMethod -> httpMethod.name().equals(method)))
			.findFirst()
			.orElse(null);

	}
}

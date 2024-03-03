package webserver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HttpResponse {

	private String responsePath;
	private StringBuilder stringBuilder;

	public HttpResponse(String responsePath) {
		this.responsePath = responsePath;
	}
}

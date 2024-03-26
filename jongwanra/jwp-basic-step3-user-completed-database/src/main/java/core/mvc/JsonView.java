package core.mvc;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonView implements View {

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws
		Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(objectMapper.writeValueAsString(model));
	}

	// private Map<String, Object> createModel(HttpServletRequest request) {
	// 	Enumeration<String> names = request.getAttributeNames();
	// 	Map<String, Object> model = new HashMap<>();
	// 	while (names.hasMoreElements()) {
	// 		String name = names.nextElement();
	// 		model.put(name, request.getAttribute(name));
	// 	}
	// 	return model;
	// }

}

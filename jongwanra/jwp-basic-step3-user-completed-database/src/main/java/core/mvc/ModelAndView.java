package core.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * JsonView, JspView
 * JsonView => Header, Filter 불필요한 정보까지 보내게 됨.
 */
public class ModelAndView {

	private View view;
	private Map<String, Object> model = new HashMap<>();

	public ModelAndView(View view) {
		this.view = view;
	}

	public ModelAndView addObject(String attributeName, Object attributeValue) {
		model.put(attributeName, attributeValue);
		return this;
	}

	public Map<String, Object> getModel() {
		return Collections.unmodifiableMap(model);
	}

	public View getView() {
		return view;
	}
}

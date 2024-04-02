package next.controller.qna;

import core.mvc.ModelAndView;

public class AppDeleteQuestionController extends DeleteQuestionController{
    @Override
    protected ModelAndView handleResponse() {
        return jsonView().addObject("result", "true");
    }
}

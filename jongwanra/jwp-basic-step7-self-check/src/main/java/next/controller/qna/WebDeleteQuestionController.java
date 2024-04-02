package next.controller.qna;

import core.mvc.ModelAndView;

public class WebDeleteQuestionController extends DeleteQuestionController{
    @Override
    protected ModelAndView handleResponse() {
        return jspView("redirect:/");
    }
}

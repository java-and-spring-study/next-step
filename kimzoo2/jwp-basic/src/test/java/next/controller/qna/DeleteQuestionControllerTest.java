package next.controller.qna;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import core.jdbc.JdbcTemplate;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.DummyQuestionDao;
import next.service.QuestionService;

//14~
public class DeleteQuestionControllerTest {

	private DeleteQuestionController deleteQuestionController;

	@Before
	public void setUp() throws Exception {
		DummyQuestionDao dao = new DummyQuestionDao(new JdbcTemplate());
		QuestionService questionService = new QuestionService(dao);
		deleteQuestionController = new DeleteQuestionController(questionService);
	}

	@Test
	public void whenDeleteQuestionThenReturnOk() throws Exception {
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		HttpServletResponse mockResponse = mock(HttpServletResponse.class);

		ModelAndView result = deleteQuestionController.execute(mockRequest, mockResponse);
		ModelAndView compareModelAndView = new ModelAndView(new JspView("redirect:/"));
		assertThat(result, is(compareModelAndView));
	}
}

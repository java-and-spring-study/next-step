package next.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Question;

public class QuestionDaoTest {

	@Before
	public void setup() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
	}

	@Test
	public void findAll() {
		QuestionDao questionDao = new QuestionDao();
		List<Question> questions = questionDao.findAll();

		assertTrue(questions.size() > 0);
	}

	@Test
	public void findBy() {
		// given
		QuestionDao questionDao = new QuestionDao();

		// when
		Question question = questionDao.findBy(1L);

		// then
		assertTrue(question != null);
	}
}

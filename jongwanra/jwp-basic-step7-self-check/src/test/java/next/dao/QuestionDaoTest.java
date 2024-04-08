package next.dao;

import core.jdbc.InMemoryJdbcTemplate;
import core.jdbc.RDBJdbcTemplate;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Question;

public class QuestionDaoTest {
    private static final Logger log = LoggerFactory.getLogger(QuestionDaoTest.class);


    @Test
    public void crud() {
        Question question = new Question("writer", "title", "contents");
        QuestionDao questionDao = new QuestionDao(new InMemoryJdbcTemplate());
        Question savedQuestion = questionDao.insert(question);
        log.debug("question : {}", savedQuestion);
    }

}

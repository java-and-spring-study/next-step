package next.dao;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import next.model.Question;

public class QuestionDaoTest {
    private static final Logger log = LoggerFactory.getLogger(QuestionDaoTest.class);

    private JdbcTemplate jdbcTemplate;
    
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
        jdbcTemplate = new JdbcTemplate();
    }
    
    @Test
    public void crud() {
        Question question = new Question("writer", "title", "contents");
        QuestionDao questionDao = new QuestionDao(jdbcTemplate);
        Question savedQuestion = questionDao.insert(question);
        log.debug("question : {}", savedQuestion);
    }

}

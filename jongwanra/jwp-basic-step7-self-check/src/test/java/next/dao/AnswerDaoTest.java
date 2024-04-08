package next.dao;

import core.jdbc.InMemoryJdbcTemplate;
import core.jdbc.RDBJdbcTemplate;
import next.model.Answer;

import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;

import java.util.List;
import java.util.stream.Collectors;

public class AnswerDaoTest {


    @Test
    public void addAnswer() throws Exception {
        long questionId = 1L;
        Answer expected = new Answer("javajigi", "answer contents", questionId);
        AnswerDao dut = new AnswerDao(new InMemoryJdbcTemplate());
        Answer answer = dut.insert(expected);
        System.out.println("Answer : " + answer);
    }


    @Test
    public void delete() throws Exception {
        long questionId = 7L;
        AnswerDao answerDao = new AnswerDao(new InMemoryJdbcTemplate());

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        List<Long> answerIds = answers.stream()
                .map(Answer::getAnswerId)
                .collect(Collectors.toList());

        answerDao.delete(answerIds);
    }
}

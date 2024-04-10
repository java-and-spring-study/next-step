package next.dao;

import core.jdbc.JdbcTemplate;

public class JdbcTemplateFactory {
	private static JdbcTemplate jdbcTemplate;

	private JdbcTemplateFactory() {}

	public synchronized static JdbcTemplate getInstance(){
		if(jdbcTemplate == null) {
			jdbcTemplate = new JdbcTemplate();
		}
		return jdbcTemplate;
	}
}

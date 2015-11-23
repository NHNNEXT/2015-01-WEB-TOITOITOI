package cafein.resources;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class cafeDAO extends JdbcDaoSupport{
	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populater = new ResourceDatabasePopulator();
		populater.addScript(new ClassPathResource("table.sql"));
		populater.addScript(new ClassPathResource("insert.sql"));
		populater.addScript(new ClassPathResource("service.sql"));
		DatabasePopulatorUtils.execute(populater, getDataSource());
	}
}

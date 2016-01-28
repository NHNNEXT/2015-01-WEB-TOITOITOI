package cafein.file;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class AbstractDao extends JdbcDaoSupport {
	@Autowired
	private DataSource ds;
	
	@PostConstruct
	public void init() {
		super.setDataSource(ds);
	}
}

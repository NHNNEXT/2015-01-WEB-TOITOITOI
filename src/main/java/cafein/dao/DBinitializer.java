package cafein.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import cafein.post.Post;

public class DBinitializer extends JdbcDaoSupport{
	//CafeinDAO class의 DB에 table 생성,test data insert.
	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("table.sql"));
		populator.addScript(new ClassPathResource("service.sql"));
		populator.addScript(new ClassPathResource("insert.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
	}
	//우리 서비스에서는 필요하지 않은 method 임.test용.
	//DB에서 값을 꺼내오기 예시.
	public Post findByPid(int pid) {
		String sql = "select * from post WHERE pid = ?";
		RowMapper<Post> rowMapper = new RowMapper<Post>() {
			@Override
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Post(rs.getInt("pid"),rs.getString("content"),rs.getString("postingtime"),rs.getInt("liked"));
			}
		};
		return getJdbcTemplate().queryForObject(sql, rowMapper,pid);
	}
	//원래 PostDAO에 있는 method와 다름. 기존 PosDAO에서 post insert 후 방금 넣은 post를 꺼내는 함수로 return
	//addpost 기능만 잘마치면 되는 아니라 return값이 post로 설정되어있음. 분리해야할까?
	public void addPost(Post post) {
		String sql = "INSERT INTO post (cid, content) VALUES(?, ?)";
	    getJdbcTemplate().update(sql, post.getCid(), post.getContents());
	}

}

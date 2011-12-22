package hibernate.semina.dao;

import hibernate.semina.generic.Pageable;
import hibernate.semina.model.UserGroup;

import java.util.Date;

import junit.framework.Assert;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
@Transactional
public class UserGroupDaoTest {

	static final String INSERT_SQL = "insert into USER_GROUP (id, name, create_time) values(?, ?, ?)";

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	UserGroupDao dao;

	@Autowired
	JdbcTemplate jdbcTemplate;

	Date createTime;

	@Before
	@Rollback(false)
	public void before() {
		createTime = new Date();
		jdbcTemplate.update(INSERT_SQL, 1, "TestGroup1", createTime);
		jdbcTemplate.update(INSERT_SQL, 2, "TestGroup2", createTime);
		jdbcTemplate.update(INSERT_SQL, 3, "TestGroup3", createTime);
		jdbcTemplate.update(INSERT_SQL, 4, "TestGroup4", createTime);
		jdbcTemplate.update(INSERT_SQL, 5, "TestGroup5", createTime);
		jdbcTemplate.update(INSERT_SQL, 6, "TestGroup6", createTime);
		jdbcTemplate.update(INSERT_SQL, 7, "TestGroup7", createTime);
		jdbcTemplate.update(INSERT_SQL, 8, "TestGroup8", createTime);
		jdbcTemplate.update(INSERT_SQL, 9, "TestGroup9", createTime);
		jdbcTemplate.update(INSERT_SQL, 10, "TestGroup10", createTime);

		jdbcTemplate.update(INSERT_SQL, 11, "AdminGroup", createTime);
	}

	@Test
	public void testCreate() {
		UserGroup group = new UserGroup();
		group.setName("TestGroup");
		group.setCreateTime(new Date());

		dao.create(group);

		Assert.assertEquals("TestGroup", getUserGroupName(group.getId()));
	}

	@Test
	public void testFindAndCount() {
		UserGroup group = dao.findById(1L);
		Assert.assertNotNull(group);
		Assert.assertEquals("TestGroup1", group.getName());
		Assert.assertEquals(createTime, group.getCreateTime());

		Assert.assertEquals(11, dao.find().size());

		UserGroup example = new UserGroup();
		example.setName("Test");

		Assert.assertEquals(10, dao.find(example).size());
		Assert.assertEquals(3, dao.find(example, new Pageable(1, 3)).size());
		Assert.assertEquals(10, dao.count(example));

		example.setName("Admin");
		Assert.assertEquals(1, dao.find(example).size());
		Assert.assertEquals(1, dao.find(example, new Pageable(1, 3)).size());
		Assert.assertEquals(1, dao.count(example));

	}

	@Test
	public void testUpdate() {
		UserGroup group = new UserGroup();
		group.setId(1L);
		group.setName("RenameGroup");
		dao.update(group);

		// 반영 않됨???
		Assert.assertEquals("TestGroup1", getUserGroupName(group.getId()));

		// 변경된 내용을 DB에 바로 반영하기 위함...
		// flush 함수를 호출하지 않으면 해당 session이 종료될때 session이 유지 되는 동안 변경된 내용을 일괄 반영함.
		sessionFactory.getCurrentSession().flush();
		Assert.assertEquals("RenameGroup", getUserGroupName(group.getId()));

		// Entity 객체가 Persistence 상태에 있는 경우 단순히 속성을 변경한 것 만으로도 DB에 데이터가 변경됨...
		group.setName("SecondRenameGroup");
		sessionFactory.getCurrentSession().flush();

		Assert.assertEquals("SecondRenameGroup", getUserGroupName(group.getId()));
	}

	@Test
	public void testDelete() {
		UserGroup group = dao.findById(1L);
		dao.delete(group);
		sessionFactory.getCurrentSession().flush();
		Assert.assertNull(getUserGroupName(group.getId()));
	}

	private String getUserGroupName(Long groupId) {
		try {
			return jdbcTemplate.queryForObject("select name from user_group where id = ?",
					new SingleColumnRowMapper<String>(), groupId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}

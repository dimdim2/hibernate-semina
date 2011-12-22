package hibernate.semina;

import hibernate.semina.model.User;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
@Transactional
public class HibernateApiTest {

	static final String INSERT_SQL = "insert into USER (id, name, create_time) values(?, ?, ?)";

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	JdbcTemplate jdbcTemplate;

	Session session;

	@Before
	@Rollback(false)
	public void before() {
		jdbcTemplate.update(INSERT_SQL, "id0", "name0", new Date());
		jdbcTemplate.update(INSERT_SQL, "id1", "name1", new Date());
		jdbcTemplate.update(INSERT_SQL, "id2", "name2", new Date());
		jdbcTemplate.update(INSERT_SQL, "id3", "name3", new Date());
		jdbcTemplate.update(INSERT_SQL, "id4", "name4", new Date());
		jdbcTemplate.update(INSERT_SQL, "id5", "name5", new Date());
		jdbcTemplate.update(INSERT_SQL, "id6", "name6", new Date());
		jdbcTemplate.update(INSERT_SQL, "id7", "name7", new Date());
		jdbcTemplate.update(INSERT_SQL, "id8", "name8", new Date());
		jdbcTemplate.update(INSERT_SQL, "id9", "name9", new Date());

		jdbcTemplate.update(INSERT_SQL, "user1", "username1", new Date());
		jdbcTemplate.update(INSERT_SQL, "user2", "username2", new Date());
		jdbcTemplate.update(INSERT_SQL, "user3", "username3", new Date());

		session = sessionFactory.getCurrentSession();
	}

	@Test
	public void testCRUD() {
		// Read (findById)
		User user = (User)session.get(User.class, "id0");
		Assert.assertEquals(user.getId(), "id0");
		Assert.assertEquals(user.getName(), "name0");

		// Create
		user = new User("dimdim", "현대원");
		session.save(user);
		session.flush();

		Assert.assertEquals(1, jdbcTemplate.queryForInt("select count(*) from user where id = 'dimdim'"));
		Assert.assertEquals("현대원", jdbcTemplate.queryForObject("select name from user where id = 'dimdim'", String.class));

		// Update
		user.setName("딤딤이");
		session.update(user);
//		session.save(user);
		session.flush();
		Assert.assertEquals("딤딤이", jdbcTemplate.queryForObject("select name from user where id = 'dimdim'", String.class));

		// Delete
		session.delete(user);
		session.flush();
		Assert.assertEquals(0, jdbcTemplate.queryForInt("select count(*) from user where id = 'dimdim'"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCriteria_list() {
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.like("id", "id", MatchMode.START));
		List<User> users = criteria.list();
		int idx = 0;
		for (User u : users) {
			Assert.assertEquals(u.getId(), "id" + idx);
			Assert.assertEquals(u.getName(), "name" + idx++);
		}

		users = session.createCriteria(User.class)
				.add(Restrictions.like("id", "user", MatchMode.START)).list();
		Assert.assertEquals(3, users.size());
		Assert.assertEquals(users.get(0).getId(), "user1");
		Assert.assertEquals(users.get(0).getName(), "username1");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCriteria_listWithPaging() {
		Criteria criteria = session.createCriteria(User.class)
				.setFirstResult(0)
				.setMaxResults(5); // 페이징 쿼리 지원을 위한  API

		List<User> users = criteria.list();
		Assert.assertEquals(5, users.size());
	}

	@Test
	public void testCriteria_uniqueResult() {
		Criteria criteria = session.createCriteria(User.class)
				.add(Restrictions.eq("id", "user1"));

		User user = (User)criteria.uniqueResult(); // 단일 결과를 가져오기 위한 함수
		Assert.assertNotNull(user);
		Assert.assertEquals("user1", user.getId());
		Assert.assertEquals("username1", user.getName());

		// 쿼리 결과가 0개인 경우 null 리턴
		criteria = session.createCriteria(User.class)
				.add(Restrictions.eq("id", "unknown"));
		Assert.assertNull(criteria.uniqueResult());
	}

	/**
	 * 쿼리 결과가 두개 이상인 경우 uniqueResult 함수 호출 시 NonUniqueResultException 발생함.
	 */
	@Test(expected = NonUniqueResultException.class)
	public void testCriteria_uniqueResult_예외케이스() {
		Criteria criteria = session.createCriteria(User.class)
				.add(Restrictions.like("id", "user", MatchMode.ANYWHERE));
		criteria.uniqueResult();
	}


	/**
	 * Example은 @Id 어노테이션이 설정된 프로퍼티를 무시한다.
	 */
	@Test
	public void testCriteriaWithExample() {
		User exUser = new User();
		exUser.setName("username");

		Example example1 = Example.create(exUser)
				.enableLike(MatchMode.START); // String 타입의 멤버변수인 경우 like 조건을 사용하도록 세팅함;
		Criteria criteria = session.createCriteria(User.class)
				.add(example1);

		Assert.assertEquals(3, criteria.list().size());

		exUser.setName("unknown");
		Example example2 = Example.create(exUser)
				.enableLike(MatchMode.ANYWHERE)
				.excludeProperty("name"); // User 클래스에서 name 멤버 변수를 조건에서 뺀다.
		criteria = session.createCriteria(User.class)
				.add(example2);

		Assert.assertEquals(13, criteria.list().size());
	}

	@Test
	public void testHql_SelectAndDelete() {
		Query selectHql = session.createQuery("from User where id = :id")
				.setParameter("id", "user1");
		User user = (User)selectHql.uniqueResult();
		Assert.assertNotNull(user);
		Assert.assertEquals("user1", user.getId());
		Assert.assertEquals("username1", user.getName());

		Query deleteHql = session.createQuery("delete from User where name like :name")
				.setString("name", user.getName());
		int deleteCount = deleteHql.executeUpdate();
		Assert.assertEquals(1, deleteCount);
		Assert.assertEquals(0, jdbcTemplate.queryForInt("select count(*) from user where id = ?", "user1"));
	}

	@Test
	public void testSql_SelectAndDelete() {
		Query selectSql = session.createSQLQuery("select * from USER where id = :id")
				.addEntity(User.class)
				.setParameter("id", "user1");

		User user = (User)selectSql.uniqueResult();
		Assert.assertNotNull(user);
		Assert.assertEquals("user1", user.getId());
		Assert.assertEquals("username1", user.getName());

		Query deleteSql = session.createSQLQuery("delete from USER where id = :id")
				.setParameter("id", "user1");
		int deleteCount = deleteSql.executeUpdate();
		Assert.assertEquals(1, deleteCount);
		Assert.assertNull(selectSql.uniqueResult());
	}

	@Test
	public void testNamedQuery() {
		Query selectHql = session.getNamedQuery("selectUserById_hql")
				.setParameter("id", "user1");
		User user = (User)selectHql.uniqueResult();
		Assert.assertNotNull(user);
		Assert.assertEquals("user1", user.getId());
		Assert.assertEquals("username1", user.getName());

		SQLQuery selectSql = (SQLQuery)session.getNamedQuery("selectUserById_sql");
		selectSql.addEntity(User.class)
				.setParameter("id", "user1");
		user = (User)selectSql.uniqueResult();
		Assert.assertNotNull(user);
		Assert.assertEquals("user1", user.getId());
		Assert.assertEquals("username1", user.getName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSql_스칼라쿼리() {
		List<Object[]> result = session.createSQLQuery("select id, name, create_time from USER").list();
		System.out.println("===================================================================================");
		for (Object[] objects : result) {
			System.out.printf("Id:%s, Name:%s, CreateTime:%s\n", objects[0], objects[1], objects[2]);
		}
		System.out.println("===================================================================================");

		List<String> categoryNames = session.createSQLQuery("select name from USER")
				.addScalar("name")
				.list();
		System.out.println("===================================================================================");
		for (String categoryName : categoryNames) {
			System.out.printf("Name:%s\n", categoryName);
		}
		System.out.println("===================================================================================");
	}



}

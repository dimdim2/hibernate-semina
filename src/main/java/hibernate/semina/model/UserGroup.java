package hibernate.semina.model;

import hibernate.semina.generic.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

@Entity
@Table(name = "USER_GROUP")
public class UserGroup implements Model<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@ManyToMany
	@JoinTable(name = "GROUP_MENU_MAP",
			joinColumns = { @JoinColumn(name = "GROUP_ID") },
			inverseJoinColumns = { @JoinColumn(name = "MENU_ID") })
	private List<Menu> availableMenus = new ArrayList<Menu>();

	@ElementCollection
	@JoinTable(name = "GROUP_AUTH", joinColumns = @JoinColumn(name = "GROUP_ID"))
	private List<GroupAuth> authorities = new ArrayList<GroupAuth>();

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public UserGroup() {
		super();
	}

	public UserGroup(Long groupId) {
		this.id = groupId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Menu> getAvailableMenus() {
		return availableMenus;
	}

	public void setAvailableMenus(List<Menu> menus) {
		this.availableMenus = menus;
	}

	public boolean addAvailableMenu(Menu menu) {
		return this.availableMenus.add(menu);
	}

	public boolean removeAvailableMenu(Menu menu) {
		return this.availableMenus.remove(menu);
	}

	public boolean removeAvailableMenu(Long menuId) {
		Iterator<Menu> iter = availableMenus.iterator();
		while (iter.hasNext()) {
			Menu menu = iter.next();
			if (ObjectUtils.equals(menu.getId(), menuId)) {
				iter.remove();
				return true;
			}
		}

		return false;
	}

	public List<GroupAuth> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<GroupAuth> authorities) {
		this.authorities = authorities;
	}

	public boolean addAuthority(GroupAuth authority) {
		return this.authorities.add(authority);
	}

	public void clearAuthorities() {
		this.authorities.clear();
	}

	public boolean removeAuthority(GroupAuth authority) {
		return this.authorities.remove(authority);
	}

	public boolean removeAuthority(Long roleId) {
		Iterator<GroupAuth> iter = authorities.iterator();
		while (iter.hasNext()) {
			GroupAuth authority = iter.next();
			Role role = authority.getRole();
			if (role != null && ObjectUtils.equals(role.getId(), roleId)) {
				iter.remove();
				return true;
			}
		}

		return false;
	}

	public void clearAvailableMenus() {
		this.availableMenus.clear();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean hasRole(Long roleId) {
		for (GroupAuth auth : authorities) {
			Role role = auth.getRole();
			if (role.getId().equals(roleId)) {
				return true;
			}
		}

		return false;
	}

}

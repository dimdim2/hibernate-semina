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
	@JoinTable(name = "MENU_GROUP_MAP",
			joinColumns = { @JoinColumn(name = "GROUP_ID") },
			inverseJoinColumns = { @JoinColumn(name = "MENU_ID") })
	private List<Menu> availableMenus = new ArrayList<Menu>();

	@ElementCollection
	@JoinTable(name = "AUTHORITY", joinColumns = @JoinColumn(name = "GROUP_ID"))
	private List<Authority> authorities = new ArrayList<Authority>();

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

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

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public boolean addAuthority(Authority authority) {
		return this.authorities.add(authority);
	}

	public boolean removeAuthority(Authority authority) {
		return this.authorities.remove(authority);
	}

	public boolean removeAuthority(Long roleId) {
		Iterator<Authority> iter = authorities.iterator();
		while (iter.hasNext()) {
			Authority authority = iter.next();
			Role role = authority.getRole();
			if (role != null && ObjectUtils.equals(role.getId(), roleId)) {
				iter.remove();
				return true;
			}
		}

		return false;
	}

	public boolean removeAllMenu() {
		return this.availableMenus.removeAll(availableMenus);
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

}

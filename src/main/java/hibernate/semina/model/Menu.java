package hibernate.semina.model;

import hibernate.semina.generic.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.lang.ObjectUtils;

@Entity
public class Menu implements Model<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "PARENT_ID")
	private Long parentId;

	private String name;

	@Enumerated(EnumType.STRING)
	private MenuType type;

	private String url;

	private String description;

	@ManyToMany
	@JoinTable(name = "GROUP_MENU_MAP",
			joinColumns = { @JoinColumn(name = "MENU_ID") },
			inverseJoinColumns = { @JoinColumn(name = "GROUP_ID") })
	private List<UserGroup> allowGroups = new ArrayList<UserGroup>();

	@Column(name = "MENU_ORDER")
	private Integer order;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public MenuType getType() {
		return type;
	}

	public void setType(MenuType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public List<UserGroup> getAllowGroups() {
		return allowGroups;
	}

	public void setAllowGroups(List<UserGroup> allowGroups) {
		this.allowGroups = allowGroups;
	}

	public boolean addAllowGroup(UserGroup userGroup) {
		return this.allowGroups.add(userGroup);
	}

	public void clearAllowGroups() {
		this.allowGroups.clear();
	}

	public boolean removeAllowGroup(UserGroup userGroup) {
		return this.allowGroups.remove(userGroup);
	}

	public boolean removeAllowGroup(Long groupId) {
		Iterator<UserGroup> iter = allowGroups.iterator();
		while (iter.hasNext()) {
			UserGroup userGroup = iter.next();
			if (ObjectUtils.equals(userGroup.getId(), groupId)) {
				iter.remove();
				return true;
			}
		}

		return false;
	}

	public boolean isAllowedGroup(Long groupId) {
		for (UserGroup group : this.allowGroups) {
			if(groupId.equals(group.getId())) {
				return true;
			}
		}

		return false;
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

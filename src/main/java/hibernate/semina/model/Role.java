package hibernate.semina.model;

import hibernate.semina.generic.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.ObjectUtils;

@Entity
public class Role implements Model<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@OneToMany(mappedBy = "role", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<Function> functions = new ArrayList<Function>();

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public Role() {
		super();
	}

	public Role(Long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		for (Function function : functions) {
			function.setRole(this);
		}

		this.functions = functions;
	}

	public boolean addFunction(Function function) {
		function.setRole(this);
		return this.functions.add(function);
	}

	public void removeAllFunction() {
		this.functions.removeAll(functions);
	}

	public boolean removeFunction(Function function) {
		return this.functions.remove(function);
	}

	public boolean removeFunction(Long functionId) {
		Iterator<Function> iter = functions.iterator();
		while (iter.hasNext()) {
			Function function = iter.next();
			if (ObjectUtils.equals(function.getId(), functionId)) {
				iter.remove();
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

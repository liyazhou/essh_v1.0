package com.eryansky.entity.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.eryansky.common.orm.entity.BaseEntity;
import com.eryansky.common.utils.ConvertUtils;
import com.eryansky.common.utils.collections.Collections3;
import com.eryansky.entity.base.state.StatusState;
import com.eryansky.utils.CacheConstants;

/**
 * 角色管理Role.
 * @author 尔演&Eryan eryanwcp@gmail.com
 * @date 2013-3-21 上午12:27:56 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_BASE_ROLE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region = CacheConstants.HIBERNATE_CACHE_BASE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties (value = { "hibernateLazyInitializer" , "handler","fieldHandler" , "resources","users"})
public class Role
        extends BaseEntity
        implements Serializable {

    /**
     * 角色名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 关联的资源
     */
    private List<Resource> resources = Lists.newArrayList();
    /**
     * 关联资源ID集合    @Transient
     */
    private List<Long> resourceIds = Lists.newArrayList();
    
    /**
     * 关联的用户
     */
    private List<User> users = Lists.newArrayList();
    


    public Role() {

    }


    public Role(String name, String description, List<Resource> resources) {

        this.name = name;
        this.description = description;
        this.resources = resources;
    }
    
	@Column(length = 100,nullable = false,unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany
    @JoinTable(name = "T_BASE_ROLE_RESOURCE", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "RESOURCE_ID") })
//    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region = CacheConstants.HIBERNATE_CACHE_BASE)
    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }


    /**
     * 角色拥有的资源id字符串集合
     *
     * @return
     */
    @Transient
    @SuppressWarnings("unchecked")
    public List<Long> getResourceIds() {
        if (!Collections3.isEmpty(resources)) {
            resourceIds = ConvertUtils.convertElementPropertyToList(resources, "id");
        }
        return resourceIds;
    }

    public void setResourceIds(List<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }

    /**
     * 角色拥有的资源字符串,多个之间以","分割
     * 
     * @return
     */
    @Transient
    public String getResourceNames() {
    	List<Resource> ms = Lists.newArrayList();
    	for(Resource m: resources){
    		if(m.getStatus() == StatusState.normal.getValue()){
    			ms.add(m);
    		}
    	}
        return ConvertUtils.convertElementPropertyToString(ms, "name",
                ", ");
    }

    /**
     * 用户拥有的角色字符串,多个之间以","分割
     * 
     * @return
     */
    @Transient
    public String getUserNames() {
        return ConvertUtils.convertElementPropertyToString(users,
                "loginName", ", ");
    }


    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region = CacheConstants.HIBERNATE_CACHE_BASE)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

package com.robote.spring.shiro.chapter6.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.robote.spring.shiro.chapter6.JdbcTemplateUtils;
import com.robote.spring.shiro.chapter6.entity.Permission;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * <p>@Title:PermissionDaoImpl.java</p>
 * <p>@Description: </p>
 * <p>@Copyright: Copyright (c) 2016</p>
 * <p>@Company: 玖富时代</p>
 * @Created on 2017年8月29日下午5:17:17
 * @author luomeng@9fbank.com.cn
 * @version 1.0
 */
public class PermissionDaoImpl implements PermissionDao{
	
	private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.jdbcTemplate();
	
	public Permission createPermission(final Permission permission) {
        final String sql = "insert into sys_permissions(permission, description, available) values(?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql,  new String[] { "id" });
                psst.setString(1, permission.getPermission());
                psst.setString(2, permission.getDescription());
                psst.setBoolean(3, permission.getAvailable());
                return psst;
            }
        }, keyHolder);
        permission.setId(keyHolder.getKey().longValue());

        return permission;
    }

    public void deletePermission(Long permissionId) {
        //首先把与permission关联的相关表的数据删掉
        String sql = "delete from sys_roles_permissions where permission_id=?";
        jdbcTemplate.update(sql, permissionId);

        sql = "delete from sys_permissions where id=?";
        jdbcTemplate.update(sql, permissionId);
    }
	
}

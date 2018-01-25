package com.cotton.dao.provider;

import java.util.Map;

import com.cotton.util.MybatisUtil;

public class UserProvider {

	private String TABLE_NAME = "t_user";
	
	/**
	 * 分页获取数据
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:22:35
	 */
	public String page(Map<String, Object> params) {
		Integer pageNum = (Integer) params.get("pageNum");
		Integer pageSize = (Integer) params.get("pageSize");
		
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(TABLE_NAME);
		sql.append(" order by id desc ");
		sql.append(MybatisUtil.limitSql(pageNum, pageSize));
		return sql.toString();
	}
	

	/**
	 * 获取分页记录总数
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:22:21
	 */
	public String count() {
		StringBuilder sql = new StringBuilder("select count(*) from ");
		sql.append(TABLE_NAME);
		return sql.toString();
	}
}
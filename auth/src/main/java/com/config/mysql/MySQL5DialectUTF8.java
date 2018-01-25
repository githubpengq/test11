package com.config.mysql;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * 解决数据库乱码
 *
 * @author yongfei.zheng
 * @date 2017/5/27 16:17 $
 */
public class MySQL5DialectUTF8 extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}

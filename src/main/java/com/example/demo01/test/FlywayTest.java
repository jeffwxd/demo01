package com.example.demo01.test;

import org.flywaydb.core.Flyway;

/**
 * @author wxd
 * @date 2020/4/17 10:43
 */
public class FlywayTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&rewriteBatchedStatements=true&useSSL=false&serverTimezone=GMT%2B8";
        String user = "root";
        String password = "1357";
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();

        // 创建 flyway_schema_history 表
//		flyway.baseline();

        // 删除 flyway_schema_history 表中失败的记录
//		flyway.repair();

        // 检查 sql 文件
//		flyway.validate();

        // 执行数据迁移
        flyway.migrate();

        // 删除当前 schema 下所有表
//		flyway.clean();
    }
}

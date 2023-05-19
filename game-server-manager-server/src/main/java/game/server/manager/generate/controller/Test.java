package game.server.manager.generate.controller;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author laoyu
 * @version 1.0
 * @description TODO
 * @date 2023/5/15
 */

public class Test {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://82.157.242.151:30208/game-manager?serverTimezone=GMT%2B8";
        String jdbcUsername = "root";
        String jdbcPassword = "vZmPqfyLeXwNsCHbKqUB5VbmgEXZyRDK";

        // 获取 JDBC 链接
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            // 获取 jOOQ 执行器
            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
            // fetch方法可以返回一个结果集对象 Result
            // jOOQ的Result对象实现了List接口，可以直接当做集合使用
            Result recordResult = dslContext.select().from("application").fetch();
            recordResult.forEach(record -> {
                System.out.println(record);
            });
//
//            // 通过 Record.into 方法可以将默认Record对象，转换为表的Record对象，例如S1UserRecord
//            // Result 接口也定义了into方法，可以将整个结果集转换为指定表Record的结果集
//            // 通过 S1UserRecord 可以通过get方法直接获得表对象
//            // 所有表的XXXRecord对象都是实现了 Record 对象的子类
//            Result<S1UserRecord> userRecordResult = recordResult.into(S1_USER);
//            userRecordResult.forEach(record -> {
//                Integer id = record.getId();
//                String username = record.getUsername();
//                System.out.println("into S1UserRecord   id: " + id + " , username: " + username);
//            });
//
//            // fetchInto方法可以可以传入任意class类型，或者表常量
//            // 会直接返回任意class类型的List集合，或者指定表Record的结果集对象
//            List<S1UserRecord> fetchIntoClassResultList = dslContext.select().from(S1_USER).fetchInto(S1UserRecord.class);
//            Result<S1UserRecord> fetchIntoTableResultList = dslContext.select().from(S1_USER).fetchInto(S1_USER);
//
//            System.out.println("fetchIntoClassResultList: \n" + fetchIntoClassResultList.toString());
//            System.out.println("fetchIntoTableResultList: \n" + fetchIntoTableResultList.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

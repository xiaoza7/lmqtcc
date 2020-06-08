package com.lmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 *
 */
@RestController
public class BusinessController {

    @Autowired
    private JdbcTemplate jdbcTemplate1;
    @Autowired
    private DataSourceTransactionManager transactionManager1;

    @Autowired
    private JdbcTemplate jdbcTemplate2;
    @Autowired
    private DataSourceTransactionManager transactionManager2;

    @RequestMapping("/b1")
    public Integer doBusiness1() throws SQLException {

        TransactionStatus status = null;

        int update;
        try {
            status =  begin1();
            update = jdbcTemplate1.update("UPDATE user_account SET balance = balance -100 WHERE id = 1");
           // int o = 1/0;
            commit1(status);
        } catch (Exception e) {
            update = -100;
            e.printStackTrace();
            rollback1(status);
        }
        return update;
    }


    @RequestMapping("/b2")
    public Integer doBusiness2() {

        int update = jdbcTemplate2.update("UPDATE user_account SET balance = balance -100 WHERE id = 2");
        return update;
    }

    /**
     * 开启事务1
     * @return
     */
    public TransactionStatus begin1(){
        return beginTransaction(transactionManager1);
    }

    /**
     * 提交事务1
     * @param status
     */
    public void commit1(TransactionStatus status){
        commitTransaction(transactionManager1,status);
    }

    /**
     * 回滚事务1
     * @param status
     */
    public void rollback1(TransactionStatus status){
        rollbackTransaction(transactionManager1,status);
    }

    /**
     * 开启事务2
     * @return
     */
    public TransactionStatus begin2(){
        return beginTransaction(transactionManager2);
    }

    /**
     * 提交事务2
     * @param status
     */
    public void commit2(TransactionStatus status){
        commitTransaction(transactionManager2,status);
    }

    /**
     * 回滚事务2
     * @param status
     */
    public void rollback2(TransactionStatus status){
        rollbackTransaction(transactionManager2,status);
    }


    /**
     * 开启事务
     */
    public TransactionStatus beginTransaction(DataSourceTransactionManager transactionManager){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();//事务定义类
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);// 返回事务对象
        return status;
    }

    /**
     * 提交事务
     * @param transactionManager
     * @param status
     */
    public void commitTransaction(DataSourceTransactionManager transactionManager,TransactionStatus status){
        transactionManager.commit(status);
    }

    /**
     * 事务回滚
     * @param transactionManager
     * @param status
     */
    public void rollbackTransaction(DataSourceTransactionManager transactionManager,TransactionStatus status){
        transactionManager.rollback(status);
    }
}

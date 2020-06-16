/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.lmqtcc.tcc.transactions;



/**
 * 事务配置器接口
 * Created by changmingxie on 11/10/15.
 */
public interface TransactionConfigurator {

	/**
	 * 获取事务管理器.
	 * @return
	 */
    public TransactionManager getTransactionManager();

    /**
     * 获取事务库.
     * @return
     */
    public TransactionRepository getTransactionRepository();

    /**
     * 获取事务恢复配置.
     * @return
     */
    public RecoverConfig getRecoverConfig();

}

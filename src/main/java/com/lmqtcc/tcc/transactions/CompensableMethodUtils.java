/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.lmqtcc.tcc.transactions;

import com.lmqtcc.tcc.common.MethodType;


/**
 * 可补偿方法工具类.
 *
 */
public class CompensableMethodUtils {

	/**
	 * 计算方法类型.
	 * @param transactionContext
	 * @param isCompensable
	 * @return
	 */
    public static MethodType calculateMethodType(TransactionContext transactionContext, boolean isCompensable) {

        if (transactionContext == null && isCompensable) {
        	// 没有事务上下文信息，并且方法有事务注解的，为可补偿事务根方法（也就是事务发起者）
            //isRootTransactionMethod
            return MethodType.ROOT;
        } else if (transactionContext == null && !isCompensable) {
        	// 没有事务上下文信息，并且方法没有事务注解的，为可补偿事务服务消费者（参考者）方法（一般为被调用的服务接口）
            //isSoaConsumer
            return MethodType.CONSUMER;
        } else if (transactionContext != null && isCompensable) {
        	// 有事务上下文信息，并且方法有事务注解的，为可补偿事务服务提供者方法（一般为被调用的服务接口的实现方法）
            //isSoaProvider
            return MethodType.PROVIDER;
        } else {
            return MethodType.NORMAL;
        }
    }

    /**
     * 获取事务上下文参数的位置.
     * @param parameterTypes
     * @return
     */
    public static int getTransactionContextParamPosition(Class<?>[] parameterTypes) {

        int i = -1;

        for (i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i].equals(TransactionContext.class)) {
                break;
            }
        }
        return i;
    }

    /**
     * 从参数获取事务上下文.
     * @param args
     * @return
     */
    public static TransactionContext getTransactionContextFromArgs(Object[] args) {

        TransactionContext transactionContext = null;

        for (Object arg : args) {
            if (arg != null && TransactionContext.class.isAssignableFrom(arg.getClass())) {

                transactionContext = (TransactionContext) arg;
            }
        }

        return transactionContext;
    }
}

package com.zhenxuan.tradeapi.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

@Component
public class ApplicationContext {

    @Autowired
    @Qualifier(value = "transactionManagerAdapter")
    private PlatformTransactionManager transactionManager;

    @Autowired
    @Qualifier(value = "transactionDefinitionDefault")
    private TransactionDefinition transactionDefinition;

    public TransactionStatus getTransactionStatus() {
        return transactionManager.getTransaction(transactionDefinition);
    }

    public void transactionCommit(TransactionStatus transactionStatus) {
        transactionManager.commit(transactionStatus);
    }

    public void transactionRollback(TransactionStatus transactionStatus) {
        transactionManager.rollback(transactionStatus);
    }
}

package com.wmh.programmeTransaction.service;

import com.wmh.programmeTransaction.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by 周大侠
 * 2018-11-24 11:07
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public void add(String id, String name) {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

            userDao.add(id, name);
            int i = 1 / 0;
            if (transactionStatus != null) {
                System.out.println("提交事务");
                transactionManager.commit(transactionStatus);
            }


        } catch (Exception e) {
            if(transactionStatus!=null){
                System.out.println("回滾事務");
                transactionManager.rollback(transactionStatus);
            }
        }

    }


}

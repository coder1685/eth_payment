package com.eth.payment.authenticate;

import com.eth.payment.config.ResourceConfig;
import com.eth.payment.fileutils.FileUtils;
import com.eth.payment.model.Web3jTransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.tx.ClientTransactionManager;

@Service
public class AuthenticationService {

    @Autowired
    ResourceConfig resourceConfig;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    Web3jTransactionModel web3jTransactionModel;

    public Credentials credentials(String primaryKey) {
        return Credentials.create(primaryKey);
    }

    public ClientTransactionManager clientTransactionManager(String accountName) {
        return new ClientTransactionManager(web3jTransactionModel.getWeb3j(), resourceConfig.getAccountAddress(accountName));
    }
}

package com.eth.payment.model;

import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ClientTransactionManager;

import java.util.Date;
import java.util.List;

@Component
public class SalaryAccountSchedulerModel extends SchedulerModel {

    public String reportName;

    public String accountName;

    public Date date;

    public List<String> files;

    public List<EthAddressDetailsModel> listEthereumAddressDetails;

    public Credentials credentials;

    public ClientTransactionManager clientTransactionManager;

    public Web3j web3j;

}

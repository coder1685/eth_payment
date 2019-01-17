package com.eth.payment.model;

import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ClientTransactionManager;

import java.util.*;

@Component
public class SchedulerModel {

    private String reportName;

    private String accountName;

    private Date date;

    private List<String> files = new ArrayList<>();

    private List<EthAddressDetailsModel> listEthereumAddressDetails;

    private Map<String, Date> accountNameDateMap = new HashMap<>();

    private Credentials credentials;

    private ClientTransactionManager clientTransactionManager;

    private Web3j web3j;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<EthAddressDetailsModel> getListEthereumAddressDetails() {
        return listEthereumAddressDetails;
    }

    public void setListEthereumAddressDetails(List<EthAddressDetailsModel> listEthereumAddressDetails) {
        this.listEthereumAddressDetails = listEthereumAddressDetails;
    }

    public Map<String, Date> getAccountNameDateMap() {
        return accountNameDateMap;
    }

    public void setAccountNameDateMap(Map<String, Date> accountNameDateMap) {
        this.accountNameDateMap = accountNameDateMap;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public ClientTransactionManager getClientTransactionManager() {
        return clientTransactionManager;
    }

    public void setClientTransactionManager(ClientTransactionManager clientTransactionManager) {
        this.clientTransactionManager = clientTransactionManager;
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }
}

package com.eth.payment.model;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ClientTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Repository
public class Web3jTransactionModel {

    private Web3j web3j;
    private Credentials credentials;
    private ClientTransactionManager clientTransactionManager;
    private String csvAccountName;
    private String accountName;
    private Iterable<CSVRecord> csvRecords;
    private Map<String, String> accountPrimaryKeyMap = new HashMap<>();
    private String primaryKey;

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


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public Iterable<CSVRecord> getCsvRecords() {
        return csvRecords;
    }

    public void setCsvRecords(Iterable<CSVRecord> csvRecords) {
        this.csvRecords = csvRecords;
    }

    public String getCsvAccountName() {
        return csvAccountName;
    }

    public void setCsvAccountName(String csvAccountName) {
        this.csvAccountName = csvAccountName;
    }

    public Map<String, String> getAccountPrimaryKeyMap() {
        return accountPrimaryKeyMap;
    }

    public void setAccountPrimaryKeyMap(Map<String, String> accountPrimaryKeyMap) {
        this.accountPrimaryKeyMap = accountPrimaryKeyMap;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}

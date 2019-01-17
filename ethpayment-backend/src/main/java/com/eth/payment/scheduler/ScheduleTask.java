package com.eth.payment.scheduler;

import com.eth.payment.csvutils.CsvUtils;
import com.eth.payment.csvutils.CsvUtilsException;
import com.eth.payment.model.EthAddressDetailsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ClientTransactionManager;

import java.util.List;

public interface ScheduleTask {

    String SALARY_ACCOUNT = "salaryAccount";
    String MARKETING_ACCOUNT = "marketingAccount";
    String FINANCE_ACCOUNT = "financeAccount";
    String GENEAL_EXPENSES = "generalExpenses";

    Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    default void executeTask(String reportName,
                             List<EthAddressDetailsModel> listEthereumAddressDetails,
                             Web3j web3j,
                             Credentials credentials,
                             ClientTransactionManager clientTransactionManager){
        try {
            new CsvUtils().createReport(reportName, listEthereumAddressDetails, web3j, credentials, clientTransactionManager);
        } catch (CsvUtilsException e) {
            log.warn("Csv report failed.", e);
        }
    }
}

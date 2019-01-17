package com.eth.payment.scheduler;

import com.eth.payment.authenticate.AuthenticationService;
import com.eth.payment.authenticate.AuthenticationServiceException;
import com.eth.payment.csvutils.CsvUtils;
import com.eth.payment.model.GeneralExpensesSchedulerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

@Component
public class ScheduleTransactionGeneralExpenses extends TimerTask implements ScheduleTask {

    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(ScheduleTransactionGeneralExpenses.class);

    private GeneralExpensesSchedulerModel generalExpensesSchedulerModel;

    public void run() {
        executeTask(generalExpensesSchedulerModel.reportName,
                generalExpensesSchedulerModel.listEthereumAddressDetails,
                generalExpensesSchedulerModel.web3j,
                generalExpensesSchedulerModel.credentials,
                generalExpensesSchedulerModel.clientTransactionManager);
        setFileList(generalExpensesSchedulerModel.files);
        log.info("Transaction after scheduled for GeneralExpensesSchedulerModel ****************************" +
                " time  " + dateFormatter.format(new Date()) +
                " reportName " + generalExpensesSchedulerModel.accountName +
                " files " + generalExpensesSchedulerModel.files);
    }

    private void setFileList(List<String> files) {
        files.add(generalExpensesSchedulerModel.reportName);
        generalExpensesSchedulerModel.setFiles(files);
    }

    public void setGeneralExpensesSchedulerModel(GeneralExpensesSchedulerModel generalExpensesSchedulerModel) {
        this.generalExpensesSchedulerModel = generalExpensesSchedulerModel;
    }
}

package com.eth.payment.scheduler;

import com.eth.payment.authenticate.AuthenticationService;
import com.eth.payment.authenticate.AuthenticationServiceException;
import com.eth.payment.csvutils.CsvUtils;
import com.eth.payment.model.SalaryAccountSchedulerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

@Component
public class ScheduleTransactionSalaryAccount extends TimerTask implements ScheduleTask {

    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(ScheduleTransactionSalaryAccount.class);

    private SalaryAccountSchedulerModel salaryAccountSchedulerModel;

    public void run() {
        executeTask(salaryAccountSchedulerModel.reportName,
                salaryAccountSchedulerModel.listEthereumAddressDetails,
                salaryAccountSchedulerModel.web3j,
                salaryAccountSchedulerModel.credentials,
                salaryAccountSchedulerModel.clientTransactionManager);
        setFileList(salaryAccountSchedulerModel.files);
        log.info("Transaction after scheduled for ScheduleTransactionSalaryAccount ****************************" +
                " time  " + dateFormatter.format(new Date()) +
                " reportName " + salaryAccountSchedulerModel.accountName +
                " files " + salaryAccountSchedulerModel.files);
    }

    private void setFileList(List<String> files) {
        files.add(salaryAccountSchedulerModel.reportName);
        salaryAccountSchedulerModel.setFiles(files);
    }

    public void setSalaryAccountSchedulerModel(SalaryAccountSchedulerModel salaryAccountSchedulerModel) {
        this.salaryAccountSchedulerModel = salaryAccountSchedulerModel;
    }

}

package com.eth.payment.scheduler;

import com.eth.payment.authenticate.AuthenticationService;
import com.eth.payment.authenticate.AuthenticationServiceException;
import com.eth.payment.model.FinanceAccountSchedulerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

@Component
public class ScheduleTransactionFinanceAccount extends TimerTask implements ScheduleTask {

    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(ScheduleTransactionFinanceAccount.class);

    private FinanceAccountSchedulerModel financeAccountSchedulerModel;

    public void run() {
        executeTask(financeAccountSchedulerModel.reportName,
                financeAccountSchedulerModel.listEthereumAddressDetails,
                financeAccountSchedulerModel.web3j,
                financeAccountSchedulerModel.credentials,
                financeAccountSchedulerModel.clientTransactionManager);
        setFileList(financeAccountSchedulerModel.files);
        log.info("Transaction after scheduled for ScheduleTransactionFinanceAccount ****************************" +
                " time  " + dateFormatter.format(new Date()) +
                " reportName " + financeAccountSchedulerModel.accountName +
                " files " + financeAccountSchedulerModel.files);
    }

    private void setFileList(List<String> files) {
        files.add(financeAccountSchedulerModel.reportName);
        financeAccountSchedulerModel.setFiles(files);
    }

    public void setFinanceAccountSchedulerModel(FinanceAccountSchedulerModel financeAccountSchedulerModel) {
        this.financeAccountSchedulerModel = financeAccountSchedulerModel;
    }
}


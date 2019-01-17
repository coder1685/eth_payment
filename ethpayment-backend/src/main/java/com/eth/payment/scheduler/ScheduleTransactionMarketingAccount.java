package com.eth.payment.scheduler;

import com.eth.payment.authenticate.AuthenticationService;
import com.eth.payment.authenticate.AuthenticationServiceException;
import com.eth.payment.csvutils.CsvUtils;
import com.eth.payment.model.MarketingAccountSchedulerModel;
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
public class ScheduleTransactionMarketingAccount extends TimerTask implements ScheduleTask {

    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(ScheduleTransactionMarketingAccount.class);

    private MarketingAccountSchedulerModel marketingAccountSchedulerModel;

    public void run() {
        executeTask(marketingAccountSchedulerModel.reportName,
                marketingAccountSchedulerModel.listEthereumAddressDetails,
                marketingAccountSchedulerModel.web3j,
                marketingAccountSchedulerModel.credentials,
                marketingAccountSchedulerModel.clientTransactionManager);
        setFileList(marketingAccountSchedulerModel.files);
        log.info("Transaction after scheduled for ScheduleTransactionMarketingAccount ****************************" +
                " time  " + dateFormatter.format(new Date()) +
                " reportName " + marketingAccountSchedulerModel.accountName +
                " files " + marketingAccountSchedulerModel.files);
    }

    private void setFileList(List<String> files) {
        files.add(marketingAccountSchedulerModel.reportName);
        marketingAccountSchedulerModel.setFiles(files);
    }

    public void setMarketingAccountSchedulerModel(MarketingAccountSchedulerModel marketingAccountSchedulerModel) {
        this.marketingAccountSchedulerModel = marketingAccountSchedulerModel;
    }
}
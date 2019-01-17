package com.eth.payment.scheduler;

import com.eth.payment.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Timer;

@Service
public class SchedulerService implements ScheduleTask {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    SchedulerModel schedulerModel;

    public void schedule(SchedulerModel schedulerModel, boolean isReschedule) {
        Timer timer = new Timer();
        log.info("Transaction before scheduled for account ****************************" +
                schedulerModel.getAccountName() + " time  " + schedulerModel.getDate());
        switch (schedulerModel.getAccountName()) {
            case SALARY_ACCOUNT:
                SalaryAccountSchedulerModel salaryAccountSchedulerModel = getSalaryAccountSchedulerModel(schedulerModel);
                if (isReschedule) {
                    timer.cancel();
                }
                ScheduleTransactionSalaryAccount scheduleTransactionSalaryAccount = new ScheduleTransactionSalaryAccount();
                scheduleTransactionSalaryAccount.setSalaryAccountSchedulerModel(salaryAccountSchedulerModel);
                setDateStringInScheduler(schedulerModel);
                timer.schedule(scheduleTransactionSalaryAccount, schedulerModel.getDate());
                break;
            case MARKETING_ACCOUNT:
                MarketingAccountSchedulerModel marketingAccountSchedulerModel = getMarketingAccountSchedulerModel(schedulerModel);
                if (isReschedule) {
                    timer.cancel();
                }
                ScheduleTransactionMarketingAccount scheduleTransactionMarketingAccount = new ScheduleTransactionMarketingAccount();
                scheduleTransactionMarketingAccount.setMarketingAccountSchedulerModel(marketingAccountSchedulerModel);
                setDateStringInScheduler(schedulerModel);
                timer.schedule(scheduleTransactionMarketingAccount, schedulerModel.getDate());
                break;
            case FINANCE_ACCOUNT:
                FinanceAccountSchedulerModel financeAccountSchedulerModel = getFinanceAccountSchedulerModel(schedulerModel);
                if (isReschedule) {
                    timer.cancel();
                }
                ScheduleTransactionFinanceAccount scheduleTransactionFinanceAccount = new ScheduleTransactionFinanceAccount();
                scheduleTransactionFinanceAccount.setFinanceAccountSchedulerModel(financeAccountSchedulerModel);
                setDateStringInScheduler(schedulerModel);
                timer.schedule(scheduleTransactionFinanceAccount, schedulerModel.getDate());
                break;
            case GENEAL_EXPENSES:
                GeneralExpensesSchedulerModel generalExpensesSchedulerModel = getGeneralExpensesSchedulerModel(schedulerModel);
                if (isReschedule) {
                    timer.cancel();
                }
                ScheduleTransactionGeneralExpenses scheduleTransactionGeneralExpenses = new ScheduleTransactionGeneralExpenses();
                scheduleTransactionGeneralExpenses.setGeneralExpensesSchedulerModel(generalExpensesSchedulerModel);
                setDateStringInScheduler(schedulerModel);
                timer.schedule(scheduleTransactionGeneralExpenses, schedulerModel.getDate());
                break;
            default:
                break;
        }
    }

    private void setDateStringInScheduler(SchedulerModel schedulerModel) {
        Map<String, Date> stringDateMap = schedulerModel.getAccountNameDateMap();
        stringDateMap.put(schedulerModel.getAccountName(), schedulerModel.getDate());
        this.schedulerModel.setAccountNameDateMap(stringDateMap);
    }

    private GeneralExpensesSchedulerModel getGeneralExpensesSchedulerModel(SchedulerModel schedulerModel) {
        GeneralExpensesSchedulerModel generalExpensesSchedulerModel = new GeneralExpensesSchedulerModel();
        generalExpensesSchedulerModel.files = schedulerModel.getFiles();
        generalExpensesSchedulerModel.accountName = schedulerModel.getAccountName();
        generalExpensesSchedulerModel.reportName = schedulerModel.getReportName();
        generalExpensesSchedulerModel.listEthereumAddressDetails = schedulerModel.getListEthereumAddressDetails();
        generalExpensesSchedulerModel.date = schedulerModel.getDate();
        generalExpensesSchedulerModel.credentials = schedulerModel.getCredentials();
        generalExpensesSchedulerModel.clientTransactionManager = schedulerModel.getClientTransactionManager();
        generalExpensesSchedulerModel.web3j = schedulerModel.getWeb3j();
        return generalExpensesSchedulerModel;
    }

    private FinanceAccountSchedulerModel getFinanceAccountSchedulerModel(SchedulerModel schedulerModel) {
        FinanceAccountSchedulerModel financeAccountSchedulerModel = new FinanceAccountSchedulerModel();
        financeAccountSchedulerModel.files = schedulerModel.getFiles();
        financeAccountSchedulerModel.accountName = schedulerModel.getAccountName();
        financeAccountSchedulerModel.reportName = schedulerModel.getReportName();
        financeAccountSchedulerModel.listEthereumAddressDetails = schedulerModel.getListEthereumAddressDetails();
        financeAccountSchedulerModel.date = schedulerModel.getDate();
        financeAccountSchedulerModel.credentials = schedulerModel.getCredentials();
        financeAccountSchedulerModel.clientTransactionManager = schedulerModel.getClientTransactionManager();
        financeAccountSchedulerModel.web3j = schedulerModel.getWeb3j();
        return financeAccountSchedulerModel;
    }

    private MarketingAccountSchedulerModel getMarketingAccountSchedulerModel(SchedulerModel schedulerModel) {
        MarketingAccountSchedulerModel marketingAccountSchedulerModel = new MarketingAccountSchedulerModel();
        marketingAccountSchedulerModel.files = schedulerModel.getFiles();
        marketingAccountSchedulerModel.accountName = schedulerModel.getAccountName();
        marketingAccountSchedulerModel.reportName = schedulerModel.getReportName();
        marketingAccountSchedulerModel.listEthereumAddressDetails = schedulerModel.getListEthereumAddressDetails();
        marketingAccountSchedulerModel.date = schedulerModel.getDate();
        marketingAccountSchedulerModel.credentials = schedulerModel.getCredentials();
        marketingAccountSchedulerModel.clientTransactionManager = schedulerModel.getClientTransactionManager();
        marketingAccountSchedulerModel.web3j = schedulerModel.getWeb3j();
        return marketingAccountSchedulerModel;
    }

    private SalaryAccountSchedulerModel getSalaryAccountSchedulerModel(SchedulerModel schedulerModel) {
        SalaryAccountSchedulerModel salaryAccountSchedulerModel = new SalaryAccountSchedulerModel();
        salaryAccountSchedulerModel.files = schedulerModel.getFiles();
        salaryAccountSchedulerModel.accountName = schedulerModel.getAccountName();
        salaryAccountSchedulerModel.reportName = schedulerModel.getReportName();
        salaryAccountSchedulerModel.listEthereumAddressDetails = schedulerModel.getListEthereumAddressDetails();
        salaryAccountSchedulerModel.date = schedulerModel.getDate();
        salaryAccountSchedulerModel.credentials = schedulerModel.getCredentials();
        salaryAccountSchedulerModel.clientTransactionManager = schedulerModel.getClientTransactionManager();
        salaryAccountSchedulerModel.web3j = schedulerModel.getWeb3j();
        return salaryAccountSchedulerModel;
    }
}

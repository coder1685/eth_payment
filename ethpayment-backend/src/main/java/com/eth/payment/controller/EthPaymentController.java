package com.eth.payment.controller;

import com.eth.payment.authenticate.AccountProperties;
import com.eth.payment.authenticate.AuthenticationService;
import com.eth.payment.config.ResourceConfig;
import com.eth.payment.csvutils.CsvUtils;
import com.eth.payment.fileutils.FileUtils;
import com.eth.payment.model.EthAddressDetailsModel;
import com.eth.payment.model.SchedulerModel;
import com.eth.payment.model.Web3jTransactionModel;
import com.eth.payment.s3.S3Services;
import com.eth.payment.scheduler.SchedulerService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/ethPayment/")
public class EthPaymentController {

    private CsvUtils csvUtils;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    ResourceConfig resourceConfig;

    @Autowired
    SchedulerService schedulerService;

    @Autowired
    SchedulerModel schedulerModel;

    @Autowired
    S3Services s3Services;

    private List<String> files = new ArrayList<>();
    private Integer count = 0;

    List<EthAddressDetailsModel> listEthereumAddressDetails = new ArrayList<>();

    List<String> accountList;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    Web3jTransactionModel web3jTransactionModel;

    @Autowired
    AccountProperties accountProperties;

    private Map<String, List<EthAddressDetailsModel>> stringListMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(@RequestParam("accountName") String accountName) {
        try {
            web3jTransactionModel.setAccountName(accountProperties.accountMap.get(accountName));
            web3jTransactionModel.setCsvAccountName(accountName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAccountDetails")
    public ResponseEntity<Date> getAccountDetails() {
        try {
            if (!CollectionUtils.isEmpty(schedulerModel.getAccountNameDateMap())) {
                return ResponseEntity.ok(schedulerModel.getAccountNameDateMap()
                        .entrySet()
                        .stream()
                        .filter(map -> map.getKey().equalsIgnoreCase(web3jTransactionModel.getAccountName()))
                        .map(map -> map.getValue()).findFirst().get());
            }
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.ok(null);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accountList")
    public ResponseEntity<List<String>> selectAccount(@RequestParam("file") MultipartFile file) {
        accountList = new ArrayList<>();
        csvUtils = new CsvUtils();
        try {
            File convFile = File.createTempFile("input", ".csv");
            java.nio.file.Files.copy(file.getInputStream(), convFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            web3jTransactionModel.setCsvRecords(csvUtils.readCsvFile(convFile));
            Map<String, String> accountKeyMap = csvUtils.readFileAccounts(csvUtils.readCsvFile(convFile));
            accountList = new ArrayList<>(accountKeyMap.keySet());
            web3jTransactionModel.setAccountPrimaryKeyMap(accountKeyMap);
            accountList.forEach(
                    accountName -> {
                        Map<String, List<EthAddressDetailsModel>> listMap = csvUtils
                                .selectEthereumAddressDetailsForAccount(accountName, csvUtils.readCsvFile(convFile));
                        stringListMap.putAll(listMap);
                    }
            );
            convFile.deleteOnExit();
            return ResponseEntity.status(HttpStatus.OK).body(accountList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(accountList);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addressList")
    public ResponseEntity<List<EthAddressDetailsModel>> addressList(String accountName) {
        listEthereumAddressDetails = new ArrayList<>();
        try {
            listEthereumAddressDetails = stringListMap.get(accountName);
            return ResponseEntity.status(HttpStatus.OK).body(listEthereumAddressDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(listEthereumAddressDetails);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getallfiles")
    public ResponseEntity<List<String>> getListFiles(Model model) {

        List<String> fileNames = schedulerModel.getFiles()
                .stream().map(fileName -> s3Services.downloadFile(fileName))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(fileNames);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFileList")
    public ResponseEntity<List<String>> getFileList() {
        return ResponseEntity.ok().body(schedulerModel.getFiles());
    }

    @RequestMapping(method = RequestMethod.POST,
            path = "transferFundsAndGenerateReport")
    public ResponseEntity transferFundsAndGenerateReport(@RequestParam("date") String scheduledDate,
                                                         @RequestParam("isReschedule") String isReschedule) {
        count++;
        String reportName = web3jTransactionModel.getAccountName() + "_report" + count + ".csv";
        populateSchedulerModel(new DateTime(scheduledDate).toDate(), reportName);
        Boolean isRescheduleTransfer = Boolean.getBoolean(isReschedule);
        schedulerService.schedule(schedulerModel, isRescheduleTransfer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void populateSchedulerModel(Date date, String reportName) {
        schedulerModel.setReportName(reportName);
        schedulerModel.setListEthereumAddressDetails(listEthereumAddressDetails);
        schedulerModel.setAccountName(web3jTransactionModel.getAccountName());
        schedulerModel.setDate(date);
        schedulerModel.setFiles(files);
        schedulerModel.setClientTransactionManager(authenticationService.clientTransactionManager(web3jTransactionModel.getAccountName()));
        schedulerModel.setCredentials(authenticationService.credentials(getPrimaryKey()));
        schedulerModel.setWeb3j(web3jTransactionModel.getWeb3j());
    }

    private String getPrimaryKey() {
        return web3jTransactionModel.getAccountPrimaryKeyMap().entrySet().stream()
                .filter(
                        map -> map.getKey().equalsIgnoreCase(web3jTransactionModel.getCsvAccountName())).findFirst().get().getValue();
    }

}

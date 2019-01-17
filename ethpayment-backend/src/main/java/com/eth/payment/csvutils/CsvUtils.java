package com.eth.payment.csvutils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.eth.payment.model.EthAddressDetailsModel;
import com.eth.payment.transferfunds.TransferFunds;
import com.eth.payment.transferfunds.TransferFundsException;
import com.google.common.io.Files;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ClientTransactionManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtils {

    public static final String[] HEADERS = {"Account", "Primary Key", "Name", "EtherAddress", "Amount"};

    public static final String[] REPORT_HEADERS = {"Name", "EthereumAddress", "Amount", "Transaction Hash"};

    private final TransferFunds transferFunds;

    private final String bucketName;

    private final AmazonS3 s3client;

    private CSVPrinter csvPrinter;

    public CsvUtils() {
        this(BeanConfigUtil.getAmazonS3Client(), BeanConfigUtil.getTransferFunds(), BeanConfigUtil.getBucketName());
    }

    private CsvUtils(AmazonS3 s3client, TransferFunds transferFunds, String bucketName) {
        this.s3client = s3client;
        this.transferFunds = transferFunds;
        this.bucketName = bucketName;
    }

    public Map<String, String> readFileAccounts(Iterable<CSVRecord> records) {
        Map<String, String> accountMap = new HashMap<>();
        records.forEach(record -> {
            accountMap.put(record.get("Account"), record.get("Primary Key"));
        });
        return accountMap;
    }

    public Iterable<CSVRecord> readCsvFile(File convFile) {
        try {
            Reader in = Files.newReader(convFile, Charset.defaultCharset());
            return CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);
        } catch (IOException e) {
            return null;
        }

    }

    public Map<String, List<EthAddressDetailsModel>> selectEthereumAddressDetailsForAccount(String accountName, Iterable<CSVRecord> records) {
        List<EthAddressDetailsModel> listEthAddressDetails = new ArrayList<>();
        Map<String, List<EthAddressDetailsModel>> listMap = new HashMap<>();
        for (CSVRecord record : records) {
            String account = record.get("Account");
            if (account.equalsIgnoreCase(accountName)) {
                String etherAddress = record.get("EtherAddress");
                BigDecimal amount = new BigDecimal(record.get("Amount"));
                String name = record.get("Name");
                listEthAddressDetails.add(populateEthereumAddress(accountName, etherAddress, amount, name));
            }
        }
        listMap.put(accountName, listEthAddressDetails);
        return listMap;
    }

    private EthAddressDetailsModel populateEthereumAddress(String accountName, String etherAddress, BigDecimal amount, String name) {
        EthAddressDetailsModel ethAddressDetailsModel = new EthAddressDetailsModel();
        ethAddressDetailsModel.setAccountName(accountName);
        ethAddressDetailsModel.setEthereumAddress(etherAddress);
        ethAddressDetailsModel.setName(name);
        ethAddressDetailsModel.setAmount(amount);
        return ethAddressDetailsModel;
    }

    public void createReport(String reportName,
                             List<EthAddressDetailsModel> listEthAddressDetailsModel,
                             Web3j web3j,
                             Credentials credentials,
                             ClientTransactionManager clientTransactionManager) throws CsvUtilsException {
        try {
            FileWriter fileWriter = new FileWriter(reportName);

            csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT
                    .withHeader(REPORT_HEADERS));

            for (EthAddressDetailsModel ethAddressDetailsModel : listEthAddressDetailsModel) {
                String name = ethAddressDetailsModel.getName();
                String ethereumAddress = ethAddressDetailsModel.getEthereumAddress();
                BigDecimal amount = ethAddressDetailsModel.getAmount();
                String transactionHash = transferFunds.transferFunds(ethereumAddress, amount, credentials,
                        clientTransactionManager, web3j);
                csvPrinter.printRecord(name, ethereumAddress, amount, transactionHash);
            }
            csvPrinter.flush();
            s3client.putObject(new PutObjectRequest(bucketName, reportName, new File(reportName)));
        } catch (TransferFundsException e) {
            throw new CsvUtilsException("Failed to transfer funds.");
        } catch (IOException e) {
            throw new CsvUtilsException("Failed to create CSV Report.");
        }
    }

    public CSVPrinter getCsvPrinter() {
        return csvPrinter;
    }

    /*private AmazonS3 getAmazonS3(){
     *//*AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(S3Config.class);*//*
        //S3Config s3Config = context.getBean(S3Config.class);
        S3Config s3Config = BeanConfigUtil.getS3ConfigBean();
        //bucketName = s3Config.getBucketName();
        transferFunds = context.getBean(TransferFunds.class);
        return context.getBean(AmazonS3.class);
    }*/
}

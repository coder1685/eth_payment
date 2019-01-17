package com.eth.payment.csvutils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.eth.payment.authenticate.AuthenticationService;
import com.eth.payment.model.EthAddressDetailsModel;
import com.eth.payment.model.Web3jTransactionModel;
import com.eth.payment.transferfunds.TransferFunds;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ClientTransactionManager;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CsvUtilsTest {

    @Mock
    private TransferFunds transferFunds;

    @Mock
    private Credentials credentialls;

    @Mock
    private Web3j web3j;

    @Mock
    private AmazonS3 s3Client;

    @Mock
    ClientTransactionManager clientTransactionManager;

    @InjectMocks
    private CsvUtils csvUtils;

    private File testFile;

    private List<String> accountList;

    private Map<String, String> accountMap;

    private Iterable<CSVRecord> records;

    @Before
    public void setUp() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        testFile = new File(classLoader.getResource("test.csv").getFile());
        records = csvUtils.readCsvFile(testFile);
    }

    @Test
    public void testReadFileAccounts(){
        accountMap = csvUtils.readFileAccounts(records);
        assertNotNull(accountMap);
        assertEquals(2, accountMap.size());
    }

    @Test
    public void testReadInputFile(){
        Map<String, List<EthAddressDetailsModel>> mapEthAddressDetailsModelList = csvUtils.selectEthereumAddressDetailsForAccount("Salary Account", records);
        assertNotNull(mapEthAddressDetailsModelList);
        assertEquals(1, mapEthAddressDetailsModelList.size());
        assertEquals("Salary Account", mapEthAddressDetailsModelList.entrySet().iterator().next().getKey());
    }

    @Test
    public void testCreateReport() throws Exception {
        when(transferFunds.transferFunds(anyString(), any(BigDecimal.class),
                any(Credentials.class), any(ClientTransactionManager.class),
                any(Web3j.class))).thenReturn("transaction hash");
        csvUtils.createReport("report", getEthAddressDetailsList(), web3j, credentialls, clientTransactionManager);
        assertNotNull(csvUtils.getCsvPrinter());
        verify(s3Client).putObject(any(PutObjectRequest.class));
    }

    private List<EthAddressDetailsModel> getEthAddressDetailsList() {
        List<EthAddressDetailsModel> detailsModelList = new ArrayList<>();
        EthAddressDetailsModel ethAddressDetailsModel = new EthAddressDetailsModel();
        ethAddressDetailsModel.setEthereumAddress("ethAddress");
        ethAddressDetailsModel.setName("name");
        ethAddressDetailsModel.setAmount(new BigDecimal(1));
        detailsModelList.add(ethAddressDetailsModel);
        return detailsModelList;
    }
}

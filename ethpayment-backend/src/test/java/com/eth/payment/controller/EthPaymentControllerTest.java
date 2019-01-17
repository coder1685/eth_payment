package com.eth.payment.controller;

import com.eth.payment.authenticate.AuthenticationService;
import com.eth.payment.csvutils.CsvUtils;
import com.eth.payment.model.EthAddressDetailsModel;
import com.eth.payment.model.SchedulerModel;
import com.eth.payment.model.Web3jTransactionModel;
import com.eth.payment.s3.S3Services;
import com.eth.payment.scheduler.SchedulerService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ClientTransactionManager;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class EthPaymentControllerTest {

    private MockMvc mvc;

    @Mock
    private CsvUtils csvUtils;

    @Mock
    private Web3jTransactionModel web3jTransactionModel;

    @Mock
    private SchedulerModel schedulerModel;

    @Mock
    SchedulerService schedulerService;

    @Mock
    private S3Services s3Services;

    @Mock
    List<EthAddressDetailsModel> listEthereumAddressDetails;

    @Mock
    AuthenticationService authenticationService;

    @InjectMocks
    private EthPaymentController ethPaymentController;

    @Before
    public void before() {
        mvc = MockMvcBuilders.standaloneSetup(ethPaymentController).build();
    }

    @Test
    @Ignore
    public void testAccountList() throws Exception {
        Map<String, String> ethAddressDetailsModelList = getEthAddressPrimaryKeyMap();
        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.csv", "text/csv", "some xml".getBytes());
        when(csvUtils.readCsvFile(any())).thenReturn(getCsvRecord());
        when(csvUtils.readFileAccounts(anyCollectionOf(CSVRecord.class))).thenReturn(ethAddressDetailsModelList);
        mvc.perform(MockMvcRequestBuilders.fileUpload("/api/ethPayment/accountList")
                .file(firstFile)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllFiles() throws Exception {
        when(schedulerModel.getFiles()).thenReturn(new ArrayList<>());
        when(s3Services.downloadFile(anyString())).thenReturn("new file");
        mvc.perform(MockMvcRequestBuilders.get("/api/ethPayment/getallfiles"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetFiles() throws Exception {
        when(schedulerModel.getFiles()).thenReturn(new ArrayList<>());
        mvc.perform(MockMvcRequestBuilders.get("/api/ethPayment/getFileList"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetSelectedEthereumAddresses() throws Exception {

        when(web3jTransactionModel.getAccountName()).thenReturn("newAccount");
        doNothing().when(csvUtils).createReport(anyString(), anyList(), any(Web3j.class), any(Credentials.class), any(ClientTransactionManager.class));
        doNothing().when(schedulerService).schedule(any(SchedulerModel.class), anyBoolean());
        when(authenticationService.clientTransactionManager(anyString())).thenReturn(null);
        when(web3jTransactionModel.getCsvAccountName()).thenReturn("accountkey");
        when(web3jTransactionModel.getAccountPrimaryKeyMap()).thenReturn(getEthAddressPrimaryKeyMap());
        when(authenticationService.credentials(anyString())).thenReturn(null);
        when(web3jTransactionModel.getWeb3j()).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/ethPayment/transferFundsAndGenerateReport")
                .param("date", "2015-07-18T13:32:56.971-0400")
                .param("isReschedule", "true"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    private Map<String, String> getEthAddressPrimaryKeyMap() {
        Map<String, String> listMap = new HashMap<>();
        listMap.put("accountkey", "primaryKey");
        return listMap;
    }

    private Iterable<CSVRecord> getCsvRecord() throws IOException {
        final StringWriter sw = new StringWriter();
        final CSVFormat format = CSVFormat.DEFAULT.withNullString("NULL");
        try (final CSVPrinter printer = new CSVPrinter(sw, format)) {
            printer.printRecord("a", null, "b");
        }
        final String csvString = sw.toString();
        return format.parse(new StringReader(csvString));
    }


}

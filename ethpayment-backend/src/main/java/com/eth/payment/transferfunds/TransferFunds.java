package com.eth.payment.transferfunds;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.ChainId;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@Service
public class TransferFunds {

    public String transferFunds(String ethAddress, BigDecimal amount, Credentials credentials,
                              ClientTransactionManager clientTransactionManager,
                              Web3j web3j) throws TransferFundsException {
        try {
            org.web3j.tx.Transfer transfer = new org.web3j.tx.Transfer(web3j, clientTransactionManager);
            System.out.println("transfer=" + String.valueOf(transfer.getSyncThreshold()));

            TransactionReceiptProcessor transactionReceiptProcessor = new NoOpProcessor(web3j);

            TransactionManager transactionManager = new RawTransactionManager(
                    web3j, credentials, ChainId.MAINNET, transactionReceiptProcessor);

            com.canya.CanYaCoin canYaCoin = com.canya.CanYaCoin.load("[Your contract address]", web3j, transactionManager, GAS_PRICE, GAS_LIMIT);


            RemoteCall<TransactionReceipt> rc = transfer.sendFunds(web3j, credentials,
                    ethAddress,
                    amount,
                    Convert.Unit.ETHER
            );
            String transactionHash = rc.sendAsync().get().getTransactionHash();
            return "https://ropsten.etherscan.io/tx/"+transactionHash;
        } catch (InterruptedException e) {
            throw new TransferFundsException("Failed to transfer funds", e);
        } catch (TransactionException e) {
            throw new TransferFundsException("Failed to transfer funds", e);
        } catch (ExecutionException e) {
            throw new TransferFundsException("Failed to transfer funds", e);
        } catch (IOException e) {
            throw new TransferFundsException("Failed to transfer funds", e);
        }
    }
}


package com.eth.payment.bootstrap;

import com.eth.payment.model.Web3jTransactionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

@Component
public class EthPaymentBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(EthPaymentBootstrap.class);

    @Autowired
    Web3jTransactionModel web3jTransactionModel;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadEthereumNode();
    }

    private void loadEthereumNode() {
        try {
            Web3j web3j = Web3j.build(new HttpService(
                    "https://ropsten.infura.io/rKlhwcDuDYO1URbJat04"));

            log.info("Connected to Ethereum client version: "
                    + web3j.web3ClientVersion().send().getWeb3ClientVersion());
            web3jTransactionModel.setWeb3j(web3j);
        } catch (IOException e) {
            log.error("Exception while loading credentials", e);
        }
    }
}

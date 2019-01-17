package com.eth.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/application.properties")
public class ResourceConfig {

    @Autowired
    Environment environment;

    public String getAccountAddress(String accountAddress){
        return this.environment.getProperty(accountAddress);
    }

}

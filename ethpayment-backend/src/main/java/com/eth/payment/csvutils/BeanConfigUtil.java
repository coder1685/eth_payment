package com.eth.payment.csvutils;

import com.amazonaws.services.s3.AmazonS3;
import com.eth.payment.s3.S3Config;
import com.eth.payment.transferfunds.TransferFunds;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanConfigUtil implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static String getBucketName() {
        return getS3ConfigBean().getBucketName();
    }

    public static TransferFunds getTransferFunds() {
        return getSpringBean("transferFunds", TransferFunds.class);
    }

    public static AmazonS3 getAmazonS3Client() {
        return getSpringBean("s3client", AmazonS3.class);
    }

    private static S3Config getS3ConfigBean() {
        return getSpringBean("s3Config", S3Config.class);
    }

    private static <T> T getSpringBean(String beanName, Class<T> valueType) {
        return applicationContext.getBean(beanName, valueType);
    }
}

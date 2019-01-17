package com.eth.payment.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class S3ServicesImplTest {

    @Mock
    private AmazonS3 s3client;

    private S3ServicesImpl s3Services;

    private S3Mock api;

    @Before
    public void setUp() {
        api = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        api.start();
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("http://localhost:8001", "us-west-2");
        s3client = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        s3client.createBucket("testbucket");
        s3client.putObject("testbucket", "file/name", "contents");
        s3Services = new S3ServicesImpl("testbucket", s3client);
    }

    @Test
    public void testUploadFile() {
        String url = s3Services.downloadFile("file/name");
        api.stop();
        assertNotNull(url);
        assertThat(url, is("http://localhost:8001/testbucket/file/name"));
    }

}
## Overview 

Transfer funds to ethereum addresses provided in csv format. 

To run the backend, cd into the `ethpayment-backend` folder and run:
 
```bash
mvn spring-boot:run
swagger spec will be available at http://localhost:8080/swagger-ui.html
```

To run the frontend, cd into the `ethpayment-frontend` folder and run:
 
```bash
npm install
npm start
The application will be accessible at http://localhost:4200.
```

# Steps to run ethereum payment system

- Open Welcome page and click on Login on Welcome page
![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step1.PNG)

- Log in username/password

![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step2.PNG)

![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step3.PNG)

- Choose and upload CSV file
![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step4.PNG)

- Select Account Name
![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step5.PNG)

- Transfer funds without selecting date
![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step6.PNG)

- Select date and transfer funds

![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step7.PNG)

Funds are scheduled to be transferred on selected date and time

- If you select same account before schedule date of transfer, you can reschedule the fund transfer

![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step8.PNG)

You can select any other account for transfer funds from account list

Report can be downloaded once funds have been processed

- Report can be downloaded on the click on the link. Reports are generated with transaction hash along with ethereum address which can be verified on Ropsten Test Network``

![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step9.PNG)
![screenshot](https://github.com/ankitamadan/ethpayment/blob/master/screenshots/step10.PNG)

## Tech stack and libraries
### Backend
- [Swagger](https://swagger.io/getting-started/)
- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Web3j](https://web3j.io/)
- [Infura](https://infura.io/)
- [Ropsten Testnet](https://ropsten.etherscan.io/)
- [Spring Test](http://docs.spring.io/autorepo/docs/spring-framework/3.2.x/spring-framework-reference/html/testing.html)
- [JUnit](http://junit.org/)
- [Mockito](http://mockito.org/)
- [Maven](https://maven.apache.org/)

### Frontend
- [Angular 4](https://angular.io/)
- [Angular CLI](https://cli.angular.io/)

### Deployment

- [AWS](https://aws.amazon.com/)

### Deployed on aws

http://ethpaymentprod-env.us-east-1.elasticbeanstalk.com/

### S3 Bucket to download files

- [S3](https://docs.aws.amazon.com/AmazonS3/latest/dev/UsingBucket.html)
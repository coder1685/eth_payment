package com.eth.payment.authenticate;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public final class AccountProperties {

    public static Map<String, String> accountMap;

    private AccountProperties(){
        accountMap = new HashMap<>();
        accountMap.put("Salary Account", "salaryAccount");
        accountMap.put("General Expenses", "generalExpenses");
        accountMap.put("Finance Account", "financeAccount");
        accountMap.put("Marketing Account", "marketingAccount");
    }

}

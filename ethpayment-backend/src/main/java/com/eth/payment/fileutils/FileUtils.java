package com.eth.payment.fileutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUtils
{
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public File getAccountKeyStore(String accountName){
        File accountKeyStore = null;
        try {
            accountKeyStore = File.createTempFile(accountName,".json");
            java.nio.file.Files.copy(getFileInputStream(accountName), accountKeyStore.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            log.warn("Account keystore not found", e);
        }
        return accountKeyStore;
    }

    public List<String> getLoggedInAccountFiles(String accountName, List<String> files){
        return !CollectionUtils.isEmpty(files) ?
                files.stream().filter(a -> a.contains(accountName)).collect(Collectors.toList()) : new ArrayList<>();
    }

    private InputStream getFileInputStream(String accountName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(accountName+".json");
    }

}

package com.baggins.abcqueryapp.utils;

import com.baggins.abcqueryapp.exceptions.ResourceReadingException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ResourceFileReader {

    public String getQueryStringFromFile(String path) {
        Resource queryFileResource = new ClassPathResource(path);
        byte[] binQuery = new byte[0];

        try {
            binQuery = FileCopyUtils.copyToByteArray(queryFileResource.getInputStream());
        } catch (IOException e) {
            throw new ResourceReadingException("Cannot read resource/file at: " + path, e);
        }
        return new String(binQuery, StandardCharsets.UTF_8);
    }
}

package com.exuberant.rest.survey;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class LocalResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String filepath) {
        return new ClassPathResource(filepath.replaceAll("classpath:", ""));
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }
}

package com.sucaiji.pagecache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@SpringBootApplication
public class PageCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(PageCacheApplication.class, args);
    }

    
}


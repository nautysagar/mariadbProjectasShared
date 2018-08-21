package com.nashtech.dataCollector;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.nashtech.dataCollector.services.ConfiguationDao;
import com.nashtech.dataCollector.services.ConfigurationService;

@Profile("test")
@Configuration
public class NameServiceConfiguration {
	
	@Bean
    @Primary
    public ConfigurationService configService() {
        return Mockito.mock(ConfigurationService.class);
        
    }
	
	/*@Bean
    @Primary
    public ConfigurationService configService() {
        return Mockito.mock(ConfigurationService.class);
        
    }*/
	
	@Bean
    @Primary
    public ConfiguationDao tdao() {
        return Mockito.mock(ConfiguationDao.class);
    }

}

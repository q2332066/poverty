package com.cloudera.poverty.common.prop;

import com.cloudera.poverty.common.settings.TableauSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件属性类
 */
@Configuration
public class PropertiesInit {

    @Bean
    @ConfigurationProperties(prefix = "tableau")
    public TableauSettings getTableauSettings(){
        return new TableauSettings();
    }
}

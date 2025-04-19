/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.config;

import java.util.Properties;
import org.rod.trejo.employees.constant.Constants;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

/**
 * The Class MessageConfig.
 *
 * @author rodTrejo.
 */
@Configuration
public class MessageConfig {

  /**
   * Properties.
   *
   * @return Properties properties.
   */
  @Bean
  public Properties yamlProperties() {
    YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
    bean.setResources(new ClassPathResource(Constants.MESSAGES_YML));
    return bean.getObject();
  }

  /**
   * Message source.
   *
   * @return MessageSource message source.
   */
  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setCommonMessages(yamlProperties());
    return messageSource;
  }
}

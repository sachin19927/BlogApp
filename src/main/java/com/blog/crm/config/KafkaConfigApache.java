package com.blog.crm.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.enums.Topics;
import com.blog.crm.logger.GenericLogger;

@Configuration
public class KafkaConfigApache {

	@Autowired
	private Environment env;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void syncAuditToKafkaServer(String msg) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", env.getProperty("bootstrap.servers"));
		properties.put("key.serializer", env.getProperty("key.serializer1"));
		properties.put("value.serializer", env.getProperty("value.serializer1"));

		KafkaProducer kafkaProducer = new KafkaProducer(properties);
		try {
			kafkaProducer.send(new ProducerRecord(Topics.BLOG_LOG + "", msg));
		} catch (Exception e) {
			GenericLogger.error(ModuleName.LOGGER, this.getClass(), e);
		} finally {
			kafkaProducer.close();
		}
	}
}

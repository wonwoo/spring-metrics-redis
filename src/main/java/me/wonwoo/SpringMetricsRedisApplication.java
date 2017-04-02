package me.wonwoo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.export.MetricExportProperties;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.repository.redis.RedisMetricRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jmx.export.MBeanExporter;

@SpringBootApplication
public class SpringMetricsRedisApplication {

  private final MetricExportProperties export;

  @Autowired
  public SpringMetricsRedisApplication(MetricExportProperties export) {
    this.export = export;
  }

  @Bean
  @ExportMetricWriter
  public RedisMetricRepository redisMetricWriter(
      RedisConnectionFactory connectionFactory) {
    return new RedisMetricRepository(connectionFactory,
        this.export.getRedis().getPrefix(), this.export.getRedis().getKey());
  }

  @Bean
  @ExportMetricWriter
  public JmxMetricWriter jmxMetricWriter(
      @Qualifier("mbeanExporter") MBeanExporter exporter) {
    return new JmxMetricWriter(exporter);
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringMetricsRedisApplication.class, args);
  }
}

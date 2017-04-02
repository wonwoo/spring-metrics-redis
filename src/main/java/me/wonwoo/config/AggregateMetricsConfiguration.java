package me.wonwoo.config;

import org.springframework.boot.actuate.endpoint.MetricReaderPublicMetrics;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.aggregate.AggregateMetricReader;
import org.springframework.boot.actuate.metrics.export.MetricExportProperties;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.boot.actuate.metrics.repository.redis.RedisMetricRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
@Configuration
public class AggregateMetricsConfiguration {

  private final MetricExportProperties export;

  private final RedisConnectionFactory connectionFactory;

  public AggregateMetricsConfiguration(MetricExportProperties export,
                                       RedisConnectionFactory connectionFactory) {
    this.export = export;
    this.connectionFactory = connectionFactory;
  }

  @Bean
  public PublicMetrics metricsAggregate() {
    return new MetricReaderPublicMetrics(aggregatesMetricReader());
  }

  private MetricReader globalMetricsForAggregation() {
    return new RedisMetricRepository(this.connectionFactory,
        this.export.getRedis().getAggregatePrefix(),
        this.export.getRedis().getKey());
  }

  private MetricReader aggregatesMetricReader() {
    AggregateMetricReader repository = new AggregateMetricReader(
        globalMetricsForAggregation());
    repository.setKeyPattern(this.export.getAggregate().getKeyPattern());
    return repository;
  }

}
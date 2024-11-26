package ru.cloudfilestorage.cloudfilestorage.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.example.elasticsearch.repository")
//@ComponentScan(basePackages = { "com.example.elasticsearch" })
public class ElasticsearchClientConfiguration {
//public class ElasticsearchClientConfiguration extends AbstractElasticsearchConfiguration {
//
//    @Override
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//
//        RestClientBuilder builder = RestClient.builder(
//                        new HttpHost("localhost", 9200))
//                .setRequestConfigCallback(
//                        requestConfigBuilder -> requestConfigBuilder
//                                .setConnectionRequestTimeout(0));
//
//        return new RestHighLevelClient(builder);
//    }

}

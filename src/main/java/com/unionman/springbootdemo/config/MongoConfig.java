package com.unionman.springbootdemo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;


@Configuration
@EnableConfigurationProperties(MongoProperties.class)
public class MongoConfig extends AbstractMongoConfiguration {

    @Autowired
    private MongoProperties properties;

    @Override
    public MongoClient mongoClient(){
        //验证对象
        MongoCredential credential = MongoCredential.createCredential(properties.getUsername(),"admin",properties.getPassword());
        //连接操作对象
        MongoClientOptions options = MongoClientOptions.builder().sslEnabled(false).build();

        MongoClient mongoClient = new MongoClient(new ServerAddress(properties.getHost(), properties.getPort()),credential,options);
        return mongoClient;
    }

    @Override
    public String getDatabaseName(){
        return properties.getDatabase();
    }

    /**
     * @description 去除“_class”字段
     * @return MappingMongoConverter
     */
    @Bean
    @Override
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        MappingMongoConverter mmc = super.mappingMongoConverter();
        mmc.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mmc;
    }

}

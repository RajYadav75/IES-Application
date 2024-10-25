package in.raj.configs;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class S3Config {
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secret}")
    private String secret;
    @Value("${region}")
    private String region;
    @Bean
    public AmazonS3 s3(){

    }
}

package cn.eqxiu.mock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author will
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.eqxiu.mock.infra.persistence.mapper")
public class ECMPCustomerMockApplication {
    public static void main(String[] args) {
        SpringApplication.run(ECMPCustomerMockApplication.class, args);
    }
}
package nsu.sber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "nsu.sber.messaging.pos.iiko")
public class DigitalWaiterApp {
    public static void main(String[] args) {
        SpringApplication.run(DigitalWaiterApp.class, args);
    }
}

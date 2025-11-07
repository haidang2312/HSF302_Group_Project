package org.example.hsf302_group1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class Hsf302Group1Application {

    public static void main(String[] args) {
        SpringApplication.run(Hsf302Group1Application.class, args);

        // Mở trình duyệt sau khi server Spring Boot khởi động
        try {
            String url = "http://localhost:8080"; // URL muốn mở
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

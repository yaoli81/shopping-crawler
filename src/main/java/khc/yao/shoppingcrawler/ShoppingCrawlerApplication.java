package khc.yao.shoppingcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ShoppingCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingCrawlerApplication.class, args);
    }
}

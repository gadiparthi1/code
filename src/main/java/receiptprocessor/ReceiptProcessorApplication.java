package receiptprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "receiptprocessor", "receiptprocessor.models", "receiptprocessor.controller" })
public class ReceiptProcessorApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReceiptProcessorApplication.class, args);
	}
}

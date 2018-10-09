package net.tea.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableScheduling
public class Application {

	public static void main(String[] args) throws Exception{

		ApplicationContext ctx = SpringApplication.run(Application.class, args);

	}
}

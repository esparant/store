package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.common.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

}


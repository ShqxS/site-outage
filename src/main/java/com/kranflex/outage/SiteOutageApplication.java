package com.kranflex.outage;

import com.kranflex.outage.service.OutageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

import java.util.TimeZone;

import static com.kranflex.outage.util.Constants.UTC_TIMEZONE;

@EnableFeignClients
@SpringBootApplication
public class SiteOutageApplication {

	@Autowired
	private OutageService outageService;
	@Value("${siteId}")
	private String siteId;

	@Value("${filteredOutageTime}")
	private String filteredOutageTime;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(UTC_TIMEZONE));
		SpringApplication.run(SiteOutageApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		outageService.processOutagesForSite(siteId, filteredOutageTime);
	}


}

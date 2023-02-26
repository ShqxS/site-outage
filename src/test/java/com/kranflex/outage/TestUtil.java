package com.kranflex.outage;

import com.kranflex.outage.client.response.Device;
import com.kranflex.outage.client.response.Outage;
import com.kranflex.outage.client.response.SiteInfo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class TestUtil {
    public static SiteInfo createSiteInfo(String siteId) {

        return SiteInfo.builder()
                .id(siteId)
                .name("Norwich Pear Tree")
                .devices(createDevices())
                .build();
    }

    public static List<Device> createDevices() {
        return List.of(
                Device.builder().id("111183e7-fb90-436b-9951-63392b36bdd2").name("Battery 1").build(),
                Device.builder().id("86b5c819-6a6c-4978-8c51-a2d810bb9318").name("Battery 2").build(),
                Device.builder().id("70656668-571e-49fa-be2e-099c67d136ab").name("Battery 3").build()
        );
    }

    public static List<Outage> createOutages() {
        return List.of(
                Outage.builder()
                        .id("111183e7-fb90-436b-9951-63392b36bdd2")
                        .begin(LocalDateTime.ofInstant(Instant.parse("2022-01-05T23:53:54.676Z"), ZoneOffset.UTC))
                        .end(LocalDateTime.ofInstant(Instant.parse("2022-11-08T01:59:10.435Z"), ZoneOffset.UTC))
                        .build(),
                Outage.builder()
                        .id("03f47b2f-a6d3-4127-8606-eff198ffdfc9")
                        .begin(LocalDateTime.ofInstant(Instant.parse("2022-06-29T17:51:14.760Z"), ZoneOffset.UTC))
                        .end(LocalDateTime.ofInstant(Instant.parse("2022-07-17T00:14:52.715Z"), ZoneOffset.UTC))
                        .build(),
                Outage.builder()
                        .id("86b5c819-6a6c-4978-8c51-a2d810bb9318")
                        .begin(LocalDateTime.ofInstant(Instant.parse("2021-12-21T03:08:17.061Z"), ZoneOffset.UTC))
                        .end(LocalDateTime.ofInstant(Instant.parse("2022-04-20T14:33:12.091Z"), ZoneOffset.UTC))
                        .build(),
                Outage.builder()
                        .id("86b5c819-6a6c-4978-8c51-a2d810bb9318")
                        .begin(LocalDateTime.ofInstant(Instant.parse("2022-02-15T11:28:26.735Z"), ZoneOffset.UTC))
                        .end(LocalDateTime.ofInstant(Instant.parse("2022-08-28T03:37:48.568Z"), ZoneOffset.UTC))
                        .build(),
                Outage.builder()
                        .id("70656668-571e-49fa-be2e-099c67d136ab")
                        .begin(LocalDateTime.ofInstant(Instant.parse("2020-10-29T20:23:43.559Z"), ZoneOffset.UTC))
                        .end(LocalDateTime.ofInstant(Instant.parse("2023-05-29T00:59:55.313Z"), ZoneOffset.UTC))
                        .build(),
                Outage.builder()
                        .id("70656668-571e-49fa-be2e-099c67d136ab")
                        .begin(LocalDateTime.ofInstant(Instant.parse("2022-10-29T20:23:43.559Z"), ZoneOffset.UTC))
                        .end(LocalDateTime.ofInstant(Instant.parse("2023-05-29T00:59:55.313Z"), ZoneOffset.UTC))
                        .build(),
                Outage.builder()
                        .id("0ec8f0f4-ad8b-421b-aade-12bc11278402")
                        .begin(LocalDateTime.ofInstant(Instant.parse("2021-11-22T17:27:31.861Z"), ZoneOffset.UTC))
                        .end(LocalDateTime.ofInstant(Instant.parse("2022-11-25T03:56:37.163Z"), ZoneOffset.UTC))
                        .build()
        );
    }
}

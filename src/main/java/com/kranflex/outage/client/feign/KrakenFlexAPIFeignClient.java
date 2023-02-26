package com.kranflex.outage.client.feign;

import com.kranflex.outage.client.request.SiteOutage;
import com.kranflex.outage.client.response.Outage;
import com.kranflex.outage.client.response.SiteInfo;
import com.kranflex.outage.configuration.FeignClientConfiguration;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "krakenflex-api",
        url = "${api.krakenflex.url}",
        configuration = FeignClientConfiguration.class)
public interface KrakenFlexAPIFeignClient {


    @GetMapping("/outages")
    List<Outage> getOutages();

    @GetMapping("/site-info/{siteId}")
    SiteInfo getSiteInfo(@PathVariable String siteId);

    @PostMapping("/site-outages/{siteId}")
    void postOutagesForSite(@PathVariable String siteId, @RequestBody List<SiteOutage> siteOutages);
}

package com.kranflex.outage.service;

import com.kranflex.outage.client.feign.KrakenFlexAPIFeignClient;
import com.kranflex.outage.client.response.Device;
import com.kranflex.outage.client.response.SiteInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteService {

    private final KrakenFlexAPIFeignClient krakenFlexAPIFeignClient;

    public List<Device> getDevicesForSite(String siteId) {
        return getSiteInfo(siteId).getDevices();
    }

    private SiteInfo getSiteInfo(String siteId) {
        log.info("Getting Device Info for {}", siteId);
        return krakenFlexAPIFeignClient.getSiteInfo(siteId);
    }

}

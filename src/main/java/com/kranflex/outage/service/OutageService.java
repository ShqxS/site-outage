package com.kranflex.outage.service;

import com.kranflex.outage.client.feign.KrakenFlexAPIFeignClient;
import com.kranflex.outage.client.request.SiteOutage;
import com.kranflex.outage.client.response.Device;
import com.kranflex.outage.client.response.Outage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutageService {

    private final SiteService siteService;
    private final KrakenFlexAPIFeignClient krakenFlexAPIFeignClient;

    public void processOutagesForSite(String siteId, String filteredOutageTimeAsString) {
        List<Outage> outages = getOutages();
        List<Device> devicesForSite = siteService.getDevicesForSite(siteId);
        List<Outage> filteredOutages = filterOutagesByTimeAndDeviceName(outages, devicesForSite, filteredOutageTimeAsString);
        List<SiteOutage> newSiteOutages = createNewSiteOutages(devicesForSite, filteredOutages);
        log.info("Posting Outages for {}", siteId);
        krakenFlexAPIFeignClient.postOutagesForSite(siteId, newSiteOutages);
    }

    private List<Outage> getOutages() {
        log.info("Getting Outages");
        return krakenFlexAPIFeignClient.getOutages();
    }

    private List<Outage> filterOutagesByTimeAndDeviceName(List<Outage> outages, List<Device> devicesForSite, String filteredOutageTimeAsString) {
        LocalDateTime filteredOutageTime = LocalDateTime.ofInstant(Instant.parse(filteredOutageTimeAsString), ZoneOffset.UTC);
        List<String> deviceIds = devicesForSite.stream().map(Device::getId).toList();
        return outages.stream().filter(outage -> !outage.getBegin().isBefore(filteredOutageTime) && deviceIds.contains(outage.getId())).collect(Collectors.toList());
    }

    private List<SiteOutage> createNewSiteOutages(List<Device> devices, List<Outage> filteredOutages) {
        return filteredOutages.stream().map(outage ->
            SiteOutage.builder()
                    .id(outage.getId())
                    .name(getDeviceNameById(devices, outage.getId()))
                    .begin(outage.getBegin())
                    .end(outage.getEnd())
                    .build()
            ).collect(Collectors.toList());
    }

    private String getDeviceNameById(List<Device> devices, String id) {
        return devices.stream().filter(device -> device.getId().equals(id)).findFirst().map(Device::getName).get();
    }
}

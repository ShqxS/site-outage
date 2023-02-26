package com.kranflex.outage.service;

import com.kranflex.outage.TestUtil;
import com.kranflex.outage.client.feign.KrakenFlexAPIFeignClient;
import com.kranflex.outage.client.request.SiteOutage;
import com.kranflex.outage.client.response.Device;
import com.kranflex.outage.client.response.Outage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OutageServiceTest {

    @InjectMocks
    private OutageService underTest;

    @Mock
    private KrakenFlexAPIFeignClient krakenFlexAPIFeignClient;

    @Mock
    private SiteService siteServiceMock;

    @Captor
    private ArgumentCaptor<String> siteIdArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<SiteOutage>> outagesArgumentCaptor;

    @Test
    public void itShouldProcessOutagesForSite() {

        // Given
        String siteId = "norwich-pear-tree";
        String filteredOutageTimeAsString= "2022-01-01T00:00:00.000Z";
        List<Outage> outages = TestUtil.createOutages();
        List<Device> devices = TestUtil.createDevices();

        // When
        when(krakenFlexAPIFeignClient.getOutages()).thenReturn(outages);
        when(siteServiceMock.getDevicesForSite(siteId)).thenReturn(devices);

        underTest.processOutagesForSite(siteId, filteredOutageTimeAsString);

        // Then
        verify(krakenFlexAPIFeignClient).postOutagesForSite(siteIdArgumentCaptor.capture(), outagesArgumentCaptor.capture());
        String siteIdArgumentCaptorValue = siteIdArgumentCaptor.getValue();
        List<SiteOutage> outagesArgumentCaptorValue = outagesArgumentCaptor.getValue();
        assertEquals(siteIdArgumentCaptorValue, siteId);

        List<String> deviceIds = devices.stream().map(Device::getId).toList();
        for (SiteOutage expectedOutage: outagesArgumentCaptorValue) {
            assertTrue(deviceIds.contains(expectedOutage.getId()));
            assertFalse(expectedOutage.getBegin().isBefore(LocalDateTime.ofInstant(Instant.parse(filteredOutageTimeAsString), ZoneOffset.UTC)));
        }
    }
}

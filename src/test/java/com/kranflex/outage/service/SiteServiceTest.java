package com.kranflex.outage.service;

import com.kranflex.outage.TestUtil;
import com.kranflex.outage.client.feign.KrakenFlexAPIFeignClient;
import com.kranflex.outage.client.response.Device;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SiteServiceTest {

    @InjectMocks
    private SiteService underTest;

    @Mock
    private KrakenFlexAPIFeignClient krakenFlexAPIFeignClient;


    @Test
    public void itShouldGetgetDevicesForSite() {

        // Given
        String siteId = "norwich-pear-tree";

        // When
        when(krakenFlexAPIFeignClient.getSiteInfo(siteId)).thenReturn(TestUtil.createSiteInfo(siteId));
        List<Device> devicesForSite = underTest.getDevicesForSite(siteId);

        // Then
        assertEquals(3, devicesForSite.size());
    }

    @Test
    public void itShouldThrowsExceptionWhenSiteNotFound() {

        // Given
        String siteId = "not-exists-siteId";

        // When
        when(krakenFlexAPIFeignClient.getSiteInfo(siteId)).thenThrow(new RuntimeException("Site Not Found"));
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> underTest.getDevicesForSite(siteId)
        );

        // Then
        assertTrue(thrown.getMessage().contains("Site Not Found"));

    }
}

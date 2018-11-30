package uk.gov.defra.datareturns.test.config;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.defra.datareturns.config.DnsCacheConfiguration;

import java.security.Security;

/**
 * Tests dns configuration
 */
@RunWith(SpringRunner.class)
@Slf4j
public class DnsConfigurationTests {

    @Test
    public void testDnsCacheConfiguration() {
        // Cache forever
        final DnsCacheConfiguration cfg = new DnsCacheConfiguration();
        cfg.setTtl(-1);
        cfg.setNegativeTtl(0);
        cfg.activate();
        Assert.assertThat(Security.getProperty(DnsCacheConfiguration.DNS_CACHE_TTL_KEY), Matchers.equalToIgnoringWhiteSpace("-1"));
        Assert.assertThat(Security.getProperty(DnsCacheConfiguration.DNS_CACHE_NEGATIVE_TTL_KEY), Matchers.equalToIgnoringWhiteSpace("0"));
    }
}

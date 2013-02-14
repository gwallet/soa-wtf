package fr.gwallet.market;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

public class SoaFailsIT extends AbstractWebServiceIntegrationTestCase<MarketService, Market> {

    @Test
    public void thingThatShouldBe() throws Exception {
        //  Given
        PotatoRequest request = new PotatoRequest();
        request.setPotatoQuery("What's up Doc?");
        //  When
        PotatoResponse response = remote().potato(request);
        //  Then
        String answer = response.getPotatoAnswer();
        assertThat(answer).isEqualTo("I'm MR Potato, pleased to meet you!"); // quite simple
    }

    @Test
    public void thingThatShouldNotBe() throws Exception {
        //  Given
        CarrotRequest request = new CarrotRequest();
        request.setCarrotQuery("What's up Doc?");
        //  When
        CarrotResponse response = remote().carrot(request);
        //  Then
        String answer = response.getCarrotAnswer();
        assertThat(answer).isNull(); // Unbelievable, isn't it ? checkout the console output!
    }

    ////////////////////////////////////////////////////////////////////////////

    @Override
    protected MarketService getService() {
        try {
            return new MarketService(new URL("http://localhost:8088/mockMarketService?wsdl"));
        } catch (MalformedURLException e) { return null; }
    }

    @Override
    protected Market getPort(MarketService service) {
        return service.getMarketPort();
    }
}

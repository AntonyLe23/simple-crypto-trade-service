package org.anthonyle.simplecryptotradeservice.schedulers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;

import org.anthonyle.simplecryptotradeservice.constants.BinanceCryptoPair;
import org.anthonyle.simplecryptotradeservice.constants.HuobiCryptoPair;
import org.anthonyle.simplecryptotradeservice.dto.BinancePrice;
import org.anthonyle.simplecryptotradeservice.dto.HuobiPrice;
import org.anthonyle.simplecryptotradeservice.dto.HuobiResponse;
import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.services.AggregatedPriceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class PriceAggregationTaskTest {

  @Mock
  RestTemplate restTemplate;

  @Mock
  AggregatedPriceService aggregatedPriceService;

  @InjectMocks
  private PriceAggregationTask priceAggregationTask = new PriceAggregationTask(new RestTemplateBuilder(), aggregatedPriceService);

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(priceAggregationTask, "restTemplate", this.restTemplate);
    ReflectionTestUtils.setField(priceAggregationTask, "aggregatedPriceService", this.aggregatedPriceService);
    ReflectionTestUtils.setField(priceAggregationTask, "cryptoPairBinanceSourceUrl", "Binance exchange source url");
    ReflectionTestUtils.setField(priceAggregationTask, "cryptoPairHoubiSourceUrl", "Houbi exchange source url");
  }

  @Test
  public void should_get_best_aggregated_price_from_exchanges() {
    BinancePrice btcBinancePrice = new BinancePrice();
    btcBinancePrice.setSymbol(BinanceCryptoPair.BTCUSDT);
    btcBinancePrice.setBidPrice(new BigDecimal(50000));
    btcBinancePrice.setAskPrice(new BigDecimal(40000));

    BinancePrice ethBinancePrice = new BinancePrice();
    ethBinancePrice.setSymbol(BinanceCryptoPair.ETHUSDT);
    ethBinancePrice.setBidPrice(new BigDecimal(10000));
    ethBinancePrice.setAskPrice(new BigDecimal(5000));

    BinancePrice[] binanceResp = new BinancePrice[]{btcBinancePrice, ethBinancePrice};

    Mockito
        .when(restTemplate.getForObject(
            anyString(), eq(BinancePrice[].class)
        ))
        .thenReturn(binanceResp);

    HuobiPrice btcHuobiPrice = new HuobiPrice();
    btcHuobiPrice.setSymbol(HuobiCryptoPair.BTCUSDT);
    btcHuobiPrice.setBid(new BigDecimal(49000));
    btcHuobiPrice.setAsk(new BigDecimal(39000));

    HuobiPrice ethHuobiPrice = new HuobiPrice();
    ethHuobiPrice.setSymbol(HuobiCryptoPair.ETHUSDT);
    ethHuobiPrice.setBid(new BigDecimal(11000));
    ethHuobiPrice.setAsk(new BigDecimal(4000));

    HuobiResponse houbiResp = new HuobiResponse();
    houbiResp.setData(List.of(btcHuobiPrice, ethHuobiPrice));
    houbiResp.setStatus("ok");

    Mockito
        .when(restTemplate.getForObject(
            anyString(), eq(HuobiResponse.class)
        ))
        .thenReturn(houbiResp);

    priceAggregationTask.aggregatePrices();

    // Verify interactions and assertions
    verify(restTemplate, times(1)).getForObject(anyString(), eq(BinancePrice[].class));
    verify(restTemplate, times(1)).getForObject(anyString(), eq(HuobiResponse.class));

    ArgumentCaptor<BigDecimal> bestBidPrices = ArgumentCaptor.forClass(BigDecimal.class);
    ArgumentCaptor<BigDecimal> bestAskPrices = ArgumentCaptor.forClass(BigDecimal.class);
    ArgumentCaptor<CryptoPair> cryptoPair = ArgumentCaptor.forClass(CryptoPair.class);

    verify(aggregatedPriceService, times(2)).createNewAggregatedPrice(bestBidPrices.capture(), bestAskPrices.capture(),
        cryptoPair.capture());

    Assertions.assertThat(bestBidPrices.getAllValues()).hasSize(2);
    Assertions.assertThat(bestBidPrices.getAllValues()).hasSameElementsAs(List.of(
        new BigDecimal(50000), // best bid price for btc
        new BigDecimal(11000) // best bid price for eth
    ));

    Assertions.assertThat(bestAskPrices.getAllValues()).hasSize(2);
    Assertions.assertThat(bestAskPrices.getAllValues()).hasSameElementsAs(List.of(
        new BigDecimal(39000), // best ask price for btc
        new BigDecimal(4000) // best ask price for eth
    ));

    Assertions.assertThat(cryptoPair.getAllValues()).hasSameElementsAs(List.of(CryptoPair.BTCUSDT, CryptoPair.ETHUSDT));
  }
}

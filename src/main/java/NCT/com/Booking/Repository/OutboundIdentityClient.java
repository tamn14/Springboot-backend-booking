package NCT.com.Booking.Repository;

import NCT.com.Booking.DTO.Request.ExchangeTokenRequest;
import NCT.com.Booking.DTO.Response.ExchangeTokenResponse;
import NCT.com.Booking.DTO.Response.UserInfoOauth2;
import feign.Headers;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "outbound-identity", url = "https://oauth2.googleapis.com")
public interface OutboundIdentityClient {

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ExchangeTokenResponse exchangeTokenResponse(@RequestBody Map<String, ?> formParams);

    @GetMapping(value = "/oauth2/v2/userinfo")
    UserInfoOauth2 userInfoOauth2(@RequestHeader("Authorization") String bearerToken);
}

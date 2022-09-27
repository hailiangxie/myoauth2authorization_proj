package xie.hailiang.authserver.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.json.JSONObject;

/**
 * @author Hailiang XIE
 *
 */
@RestController
public class AuthorizeCallbackController {
	
	@GetMapping(value = "/callback")
	public String authorizeCallback(@RequestParam("code") String authCode) throws IOException {
		
		JSONObject payload = new JSONObject();
		payload.put("authorization_code", authCode);
		
		return payload.toJSONString();
	}
}

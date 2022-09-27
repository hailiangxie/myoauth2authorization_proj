package xie.hailiang.authserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Oauth2AuthServerApplication.class)
@AutoConfigureMockMvc
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Oauth2AuthServerApplicationTests {
	
	@Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
          .addFilter(springSecurityFilterChain).build();
    }
    
    private static String accessToken = "";
    private static String refreshToken = "";

	@Test
	@Order(1)
	@WithMockUser(username = "oauth2_client1", password = "123456")
	void test1_givenValidBasicAuthenciation_whenIssueToken_thenOK() throws Exception {
		MvcResult result = mockMvc.perform(post("/oauth/token")
		          .param("grant_type", "password")
		          .param("username", "user")
		          .param("password", "password")
		          .param("scope", "read")
		          )
				  .andExpectAll(status().isOk())
		          .andReturn();
		
		JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
		log.info("Token info: " + obj.toString());
		accessToken = obj.getString("access_token");
		refreshToken = obj.getString("refresh_token");
	}
	
	@Test
	@Order(2)
	@WithMockUser(username = "oauth2_client1", password = "123456")
	void test2_givenValidBasicAuthenciation_whenCheckToken_thenOK() throws Exception {
		MvcResult result = mockMvc.perform(post("/oauth/check_token")
		          .param("token", accessToken)
		          )
				  .andExpectAll(status().isOk())
		          .andReturn();
		
		JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
		String username = obj.getString("user_name");
		assertEquals("user", username);
	}
	
	@Test
	@Order(3)
	@WithMockUser(username = "oauth2_client1", password = "123456")
	void test3_givenValidBasicAuthenciation_whenPublicKey_thenOK() throws Exception {
		MvcResult result = mockMvc.perform(get("/oauth/token_key")
		          )
				  .andExpectAll(status().isOk())
		          .andReturn();
		
		JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
		String algname = obj.getString("alg");
		assertEquals("SHA256withRSA", algname);
	}
	
	@Test
	@Order(4)
	@WithMockUser(username = "oauth2_client1", password = "123456")
	void test4_givenValidBasicAuthenciation_whenIssueNewTokenByRefreshToken_thenOK() throws Exception {
		MvcResult result = mockMvc.perform(post("/oauth/token")
		          .param("grant_type", "refresh_token")
		          .param("refresh_token", refreshToken)
		          )
				  .andExpectAll(status().isOk())
		          .andReturn();
		
		JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
		log.info("New token info: " + obj.toString());
		String newAccessToken = obj.getString("access_token");
		assertThat(newAccessToken).isNotEqualTo(accessToken);
	}

}

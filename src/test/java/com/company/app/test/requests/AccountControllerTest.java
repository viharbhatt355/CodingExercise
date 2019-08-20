package com.company.app.test.requests;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;

import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.ResultMatcher;

@RunWith(OrderedTestRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private MockMvc mockMvc;

    /**
     *
     * @throws Exception
     *
     *             It tests creating account
     */
    @Test
    @Order(1)
    public void createAccounts() throws Exception {

        String json = "{" +
                "" +
                "  \"accountType\": \"MMA\"," +
                "  \"balance\": 0," +
                "  \"owner\": \"Alice\"" +
                "}";

        mockMvc.perform(
                post("/accounts").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isCreated());

        json = "{" +
                "" +
                "  \"accountType\": \"MMA\"," +
                "  \"balance\": 0," +
                "  \"owner\": \"Bob\"" +
                "}";
        mockMvc.perform(
                post("/accounts").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isCreated());

    }

    /**
     *
     * @throws Exception
     *
     *             It tests creating account
     */
    @Test
    @Order(2)
    public void updateAccountInvalidId() throws Exception {

        String json = "{" +
                "" +
                "  \"accountType\": \"MMA\"," +
                "  \"balance\": 0," +
                "  \"owner\": \"Bobby\"" +
                "}";
        mockMvc.perform(
                put("/accounts/124").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isNotFound());
    }

    /**
     *
     * @throws Exception
     *
     *             It tests finding account
     */
    @Test
    @Order(3)
    public void findAccountById() throws Exception {

        String res = "{" +
                "    \"accountId\": 1," +
                "    \"balance\": 0.00," +
                "    \"owner\": \"Alice\"," +
                "    \"accountType\": \"MMA\"" +
                "}";

        assertTrue(
                ResultMatcher.matchJson(
                        mockMvc.perform(get("/accounts/1")).andExpect(
                                status().isOk()).andReturn().getResponse().getContentAsString(),
                        res,
                        true));
    }

    /**
     *
     * @throws Exception
     *
     *             It tests finding account
     */
    @Test
    @Order(4)
    public void findAccByNonExistingId() throws Exception {
        /**
         *
         * Find account by non-existing id 19
         */
        mockMvc.perform(get("/accounts/19")).andExpect(status().isNotFound());
    }

    /**
     *
     * @throws Exception
     *
     *             It tests finding accounts by user id
     */
    @Test
    @Order(5)
    public void findAllAccounts() throws Exception {

        String res = "[" +
                "    {" +
                "        \"accountId\": 1," +
                "        \"balance\": 0.00," +
                "        \"owner\": \"Alice\"," +
                "        \"accountType\": \"MMA\"" +
                "    }," +
                "    {" +
                "        \"accountId\": 2," +
                "        \"balance\": 0.00," +
                "        \"owner\": \"Bob\"," +
                "        \"accountType\": \"MMA\"" +
                "    }" +
                "]";

        assertTrue(
                ResultMatcher.matchJsonArray(
                        mockMvc.perform(get("/accounts")).andExpect(
                                status().isOk()).andReturn().getResponse().getContentAsString(),
                        res,
                        true));
    }

    @Test
    @Order(6)
    public void updateAccount() throws Exception {

        String json = "{" +

                "  \"owner\": \"Bobby\"" +
                "}";
        mockMvc.perform(
                put("/accounts/2").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isOk());

        json = "{" +
                "    \"accountId\": 2," +
                "    \"balance\": 0.00," +
                "    \"owner\": \"Bobby\"," +
                "    \"accountType\": \"MMA\"" +
                "}";

        String updateStr = mockMvc.perform(get("/accounts/2")).andExpect(
                status().isOk()).andReturn().getResponse().getContentAsString();
        assertTrue(
                ResultMatcher.matchJson(
                        updateStr,
                        json,
                        true));

        ;

    }

}

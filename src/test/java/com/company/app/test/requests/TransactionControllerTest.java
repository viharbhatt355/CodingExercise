package com.company.app.test.requests;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class TransactionControllerTest {

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
     *             It tests creating txs
     */
    @Test
    @Order(1)
    public void testDeposits() throws Exception {

        String json = "{" +
                "  \"fromAccount\": 1," +
                "  \"toAccount\": 1," +
                "  \"txnAmount\": 1000," +
                "  \"txnType\": \"DEPOSIT\"" +
                "}";

        mockMvc.perform(
                post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isCreated());

        json = "{" +
                "  \"fromAccount\": 2," +
                "  \"toAccount\": 2," +
                "  \"txnAmount\": 2000," +
                "  \"txnType\": \"DEPOSIT\"" +
                "}";
        mockMvc.perform(
                post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isCreated());

        json = "[" +
                "    {" +
                "        \"accountId\": 1," +
                "        \"balance\": 1000.00," +
                "        \"owner\": \"Alice\"," +
                "        \"accountType\": \"MMA\"" +
                "    }," +
                "    {" +
                "        \"accountId\": 2," +
                "        \"balance\": 2000.00," +
                "        \"owner\": \"Bobby\"," +
                "        \"accountType\": \"MMA\"" +
                "    }" +
                "]";

        String accts = mockMvc.perform(get("/accounts")).andExpect(
                status().isOk()).andReturn().getResponse().getContentAsString();
        assertTrue(
                ResultMatcher.matchJsonArray(
                        accts,
                        json,
                        true));

    }

    @Test
    @Order(2)
    public void testWithDrawals() throws Exception {

        String json = "{" +
                "  \"fromAccount\": 1," +
                "  \"toAccount\": 1," +
                "  \"txnAmount\": 500," +
                "  \"txnType\": \"WITHDRAW\"" +
                "}";

        mockMvc.perform(
                post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isCreated());

        json = "{" +
                "  \"fromAccount\": 2," +
                "  \"toAccount\": 2," +
                "  \"txnAmount\": 500," +
                "  \"txnType\": \"WITHDRAW\"" +
                "}";
        mockMvc.perform(
                post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isCreated());

        json = "[" +
                "    {" +
                "        \"accountId\": 1," +
                "        \"balance\": 500.00," +
                "        \"owner\": \"Alice\"," +
                "        \"accountType\": \"MMA\"" +
                "    }," +
                "    {" +
                "        \"accountId\": 2," +
                "        \"balance\": 1500.00," +
                "        \"owner\": \"Bobby\"," +
                "        \"accountType\": \"MMA\"" +
                "    }" +
                "]";

        String accts = mockMvc.perform(get("/accounts")).andExpect(
                status().isOk()).andReturn().getResponse().getContentAsString();
        assertTrue(
                ResultMatcher.matchJsonArray(
                        accts,
                        json,
                        true));

    }

    @Test
    @Order(3)
    public void testInvalidWithDrawals() throws Exception {

        String json = "{" +
                "  \"fromAccount\": 1," +
                "  \"toAccount\": 1," +
                "  \"txnAmount\": 5000," +
                "  \"txnType\": \"WITHDRAW\"" +
                "}";

        mockMvc.perform(
                post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(
                        status().isBadRequest());

        json = "{" +
                "  \"fromAccount\": 2," +
                "  \"toAccount\": 2," +
                "  \"txnAmount\": 5000," +
                "  \"txnType\": \"WITHDRAW\"" +
                "}";
        mockMvc.perform(
                post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(
                        status().isBadRequest());

        json = "[" +
                "    {" +
                "        \"accountId\": 1," +
                "        \"balance\": 500.00," +
                "        \"owner\": \"Alice\"," +
                "        \"accountType\": \"MMA\"" +
                "    }," +
                "    {" +
                "        \"accountId\": 2," +
                "        \"balance\": 1500.00," +
                "        \"owner\": \"Bobby\"," +
                "        \"accountType\": \"MMA\"" +
                "    }" +
                "]";

        String accts = mockMvc.perform(get("/accounts")).andExpect(
                status().isOk()).andReturn().getResponse().getContentAsString();
        assertTrue(
                ResultMatcher.matchJsonArray(
                        accts,
                        json,
                        true));

    }

    @Test
    @Order(3)
    public void testTransfers() throws Exception {

        String json = "{" +
                "  \"fromAccount\": 1," +
                "  \"toAccount\": 2," +
                "  \"txnAmount\": 500," +
                "  \"txnType\": \"TRANSFER\"" +
                "}";

        mockMvc.perform(
                post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isCreated());

        json = "[" +
                "    {" +
                "        \"accountId\": 1," +
                "        \"balance\": 0.00," +
                "        \"owner\": \"Alice\"," +
                "        \"accountType\": \"MMA\"" +
                "    }," +
                "    {" +
                "        \"accountId\": 2," +
                "        \"balance\": 2000.00," +
                "        \"owner\": \"Bobby\"," +
                "        \"accountType\": \"MMA\"" +
                "    }" +
                "]";

        String accts = mockMvc.perform(get("/accounts")).andExpect(
                status().isOk()).andReturn().getResponse().getContentAsString();
        assertTrue(
                ResultMatcher.matchJsonArray(
                        accts,
                        json,
                        true));
    }

    @Test
    @Order(4)
    public void testInvalidTransfers() throws Exception {

        String json = "{" +
                "  \"fromAccount\": 1," +
                "  \"toAccount\": 2," +
                "  \"txnAmount\": 5000," +
                "  \"txnType\": \"WITHDRAW\"" +
                "}";
        mockMvc.perform(
                post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(
                        status().isBadRequest()); // No Balance
    }

}

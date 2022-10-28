package com.example.Spring3;

import com.example.Spring3.activeMQ.QueueProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.ResultSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //可以不需要完整啟動 HTTP 服務器就可以快速測試MVC 控制器。
public class QueueProducerTransferControllerTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QueueProducer queueProducer;

    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(new QueueProducerTransferController()).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }


    @Test
    @DisplayName("測試 getAllData()")
    public void getAllData() throws Exception {


        RequestBuilder request = get("/getAll"); // 構造請求get

        // 執行get請求
       mockMvc.perform(request)
                .andExpect(status().isOk());
               // 對請求結果進行期望，回覆的狀態為200

        System.out.println("測試 Mgni Find All !");
    }
}
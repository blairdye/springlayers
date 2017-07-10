package net.blairdye.springactor.layers.web;

import net.blairdye.springactor.layers.service.AskDataSourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest({WebController.class})
public class TestWebController {
    private static final Logger logger = LogManager.getLogger(TestWebController.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AskDataSourceService askDataSourceService;

    @Test
    public void canQueryWebLayer() throws Exception{
        //a test to mock out the http server and the layers below this and just test the rest controller
        String queryValue = "testtouppercase";
        String mockResponse = "TESTTOUPPERCASE";
        when(askDataSourceService.queryDataSource(queryValue)).thenReturn(mockResponse);
        logger.info("starting");
        mockMvc.perform(get("/api/web/uppercase").param("value", queryValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(mockResponse)));

    }

}

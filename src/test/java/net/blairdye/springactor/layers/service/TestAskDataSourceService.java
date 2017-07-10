package net.blairdye.springactor.layers.service;

import net.blairdye.springactor.layers.service.AskDataSourceService;
import net.blairdye.springactor.layers.web.WebController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=AskDataSourceService.class)
public class TestAskDataSourceService {
    @Autowired
    private AskDataSourceService askDataSourceService;

    @Test
    public void canAskDataSourceService(){
        String result = askDataSourceService.queryDataSource("test");
        assertEquals(result, "TEST");
    }


}

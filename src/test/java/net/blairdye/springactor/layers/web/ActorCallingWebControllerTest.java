package net.blairdye.springactor.layers.web;

import net.blairdye.springactor.layers.actor.MessageBrokerActor;
import net.blairdye.springactor.layers.actor.SpringExtension;
import net.blairdye.springactor.layers.dao.PersonDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({ActorCallingWebController.class, SpringExtension.class, MessageBrokerActor.class, PersonDao.class})
public class ActorCallingWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@MockBean
    //private

    @Test
    public void canGetResponse() throws Exception{
        mockMvc.perform(get("/api/web/actor").param("value", "test message"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Response:test message")));
    }
}
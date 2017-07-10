package net.blairdye.springactor.layers.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import net.blairdye.springactor.layers.dao.PersonDao;
import net.blairdye.springactor.layers.service.AskDataSourceService;
import net.blairdye.springactor.layers.web.ActorCallingWebController;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import scala.concurrent.duration.Duration;

import static net.blairdye.springactor.layers.actor.MessageBrokerActor.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringExtension.class, MessageBrokerActor.class, PersonDao.class})
//@WebMvcTest({SpringExtension.class, MessageBrokerActor.class, PersonDao.class})
public class ActorTestKit {
    static ActorSystem system;

    @MockBean
    private PersonDao personDao;

    @Autowired
    private SpringExtension springExtension;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("TestSystem");
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testIt() {

        //ActorSystem system = ActorSystem.create("TestSystem");
        //return system.actorOf(SPRING_EXTENSION_PROVIDER.get(system).props("messageBrokerActor")), "job-manager-supervisor");
        // create the result listener, which will print the result and shutdown the system
        //final ActorRef actor = system.actorOf(Props.create(MessageBrokerActor.class), "messagebroker");
        //ActorRef actor = system.actorOf(SPRING_EXTENSION_PROVIDER.get(system)
        //      .props("messageBrokerActor"), "greeter");

        when(personDao.getPerson("hello world")).thenReturn(new PersonDao.Person("hello world","My person"));


        new TestKit(system) {{
            //final Props props = Props.create(MessageBrokerActor.class);
            //final ActorRef subject = system.actorOf(props);
            final ActorRef actor = system.actorOf(springExtension.props(MessageBrokerActor.class), "messagebroker");

            // can also use JavaTestKit “from the outside”
            //final TestKit probe = new TestKit(system);
            // “inject” the probe by passing it to the test subject
            // like a real resource would be passed in production
            //subject.tell(probe.getRef(), getRef());
            // await the correct response
            //expectMsg(duration("1 second"), "done");

            // the run() method needs to finish within 3 seconds
            within(duration("5 seconds"), () -> {
                InboundMessage inboundMessage = new InboundMessage("hello world");
                actor.tell(inboundMessage, getRef());

                // This is a demo: would normally use expectMsgEquals().
                // Wait time is bounded by 3-second deadline above.
                //awaitCond(probe::msgAvailable);

                // response must have been enqueued to us before probe
                expectMsg(new OutboundMessage("Response:hello world"));
                // check that the probe we injected earlier got the msg
                //probe.expectMsg(Duration.Zero(), inboundMessage);
                //assertEquals(getRef(), probe.getLastSender());

                // Will wait for the rest of the 3 seconds
                expectNoMsg();
                return null;
            });
        }};
    }
}

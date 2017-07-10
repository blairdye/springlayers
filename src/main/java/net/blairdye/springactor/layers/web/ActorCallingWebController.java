package net.blairdye.springactor.layers.web;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import net.blairdye.springactor.layers.actor.MessageBrokerActor;
import net.blairdye.springactor.layers.actor.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;


/**
 * Created by blaird on 6/07/17.
 */
@RestController("AnotherWebController_1_0")
@RequestMapping("/api/web")
public class ActorCallingWebController {
    @Autowired
    private SpringExtension springExtension;

    public class Result{
        public String message;
        public Result(String message) {
            this.message = message;
        }
    }

    @GetMapping(path = "/actor",
            produces = "application/vnd.com.orchestral.hpd-v1_0+json")
    public Object search(@RequestParam(value="value",required=true)String value) throws Exception{
        ActorSystem system = ActorSystem.create("TestSystem");
        //return system.actorOf(SPRING_EXTENSION_PROVIDER.get(system).props("messageBrokerActor")), "job-manager-supervisor");
        // create the result listener, which will print the result and shutdown the system
        //final ActorRef actor = system.actorOf(Props.create(MessageBrokerActor.class), "messagebroker");
        //ActorRef actor = system.actorOf(SPRING_EXTENSION_PROVIDER.get(system)
          //      .props("messageBrokerActor"), "greeter");
        final ActorRef actor = system.actorOf(springExtension.props(MessageBrokerActor.class), "messagebroker");

        //call an actor and wait for the result

        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(actor, new MessageBrokerActor.InboundMessage(value), timeout);
        return Await.result(future, timeout.duration());

         //broker.tell(new MessageBrokerActor.InboundMessage("inbound message"),getSender())
        //return new Result("hello world");
    }

}

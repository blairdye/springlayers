package net.blairdye.springactor.layers.actor;

import akka.actor.AbstractActor;
import net.blairdye.springactor.layers.dao.PersonDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value="messageBrokerActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MessageBrokerActor extends AbstractActor {
    private static final Logger logger = LogManager.getLogger(MessageBrokerActor.class);

    @Autowired
    private PersonDao personDao;

    public static class InboundMessage {
        public String content;

        public InboundMessage(String content) {
            this.content = content;
        }
    }

    public static class OutboundMessage {
        public String content;

        public OutboundMessage(String content) {
            this.content = content;
        }

        @Override
        public boolean equals(Object o){
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            return ((OutboundMessage)o).content.equals(content);
        }
    }

    public MessageBrokerActor(){

    }
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(InboundMessage.class, this::createResponse).build();
    }


    public void createResponse(InboundMessage inboundMessage) {
        logger.info("sending response"+personDao);
        PersonDao.Person person = personDao.getPerson(inboundMessage.content);
        getSender().tell(new OutboundMessage("Response:"+person.id), getSelf());
        //getSender().tell(new OutboundMessage("Response:"+inboundMessage.content), getSelf());
    }
}

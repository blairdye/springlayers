package net.blairdye.springactor.layers.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

public class SpringActorProducer implements IndirectActorProducer {

    private final ApplicationContext applicationContext;
    private final Class<? extends Actor> actorClass;
    private final Object[] args;

    public SpringActorProducer(final ApplicationContext applicationContext, final Class<? extends Actor> actorClass, final Object... args) {
        this.applicationContext = applicationContext;
        this.actorClass = actorClass;
        this.args = args;
    }

    @Override
    public Actor produce() {
        return applicationContext.getBean(actorClass, args);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return actorClass;
    }
}


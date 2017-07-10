package net.blairdye.springactor.layers.actor;

import akka.actor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringExtension
        implements Extension {
/*
    public static final SpringExtension SPRING_EXTENSION_PROVIDER
            = new SpringExtension();

    @Override
    public SpringExt createExtension(ExtendedActorSystem system) {
        return new SpringExt();
    }

    public static class SpringExt implements Extension {
        private volatile ApplicationContext applicationContext;

        public void initialize(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public Props props(String actorBeanName) {
            return Props.create(
                    SpringActorProducer.class, applicationContext, actorBeanName);
        }
    }
*/

    @Autowired
    private ApplicationContext applicationContext;

    public Props props(final Class<? extends Actor> actorClass, final Object... args) {
        return Props.create(SpringActorProducer.class, applicationContext, actorClass, args);
    }
}

package Mooving.MUgituApi.rabbitMQ;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.ProtobufMessageConverter;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitMQConfiguration {
    public final static String EXCHANGE_NAME = "exchangeApiSimulator";
    public final static String DLX_NAME = "dlxApiSimulator";

    public final static String ESTACION_QUEUE = "queueEstacion";
    public final static String BICI_QUEUE = "queueBici";
    public final static String DLQ_NAME = "queueDeadletter";

    public final static String ROUTING_KEY_ESTACION = "estacionId";
    public final static String ROUTING_KEY_BICI = "biciId";

    // EXCHANGES
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DLX_NAME);
    }


    // QUEUES
    @Bean
    Queue estacionQueue() {
        return QueueBuilder.durable(ESTACION_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_NAME)
                .build();
    }
    @Bean
    Queue biciQueue() {
        return new Queue(BICI_QUEUE, false);
    }
    @Bean
    Queue dlQueue() {
        return new Queue(DLQ_NAME, true);
    }


    // BINDINGS
    @Bean
    Binding estacionBinding(Queue estacionQueue, DirectExchange exchange) {
        return BindingBuilder.bind(estacionQueue).to(exchange).with(ROUTING_KEY_ESTACION);
    }
    /*@Bean
    Binding biciBinding(Queue biciQueue, DirectExchange exchange) {
        return BindingBuilder.bind(biciQueue).to(exchange).with(ROUTING_KEY_BICI);
    }
    @Bean
    Binding dlBinding(Queue dlQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(dlQueue).to(exchange);
    }*/


    // ADDITIONAL CONFIG
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(50);
        factory.setConcurrentConsumers(2);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setMessageConverter(new SimpleMessageConverter());
        factory.setErrorHandler(t -> {
            if(!t.getMessage().contains("AmqpRejectAndDontRequeueException")){
                t.printStackTrace();
            }else{
                System.out.println("Denied request: "+t.getCause().getMessage());
            }
        });

        return factory;
    }

    /*@Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }*/
}


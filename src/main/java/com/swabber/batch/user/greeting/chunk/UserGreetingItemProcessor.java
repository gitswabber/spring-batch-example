package com.swabber.batch.user.greeting.chunk;

import com.swabber.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class UserGreetingItemProcessor implements ItemProcessor<User, String> {
    @Override
    public String process(User user) throws Exception {
        String greetingMessage = String.format("Hello %s %s ! You're %s.", user.getLastName(), user.getFirstName(), user.getAge());
        log.info("Write down a greeting message into file. message : '{}'", greetingMessage);
        return greetingMessage;
    }
}

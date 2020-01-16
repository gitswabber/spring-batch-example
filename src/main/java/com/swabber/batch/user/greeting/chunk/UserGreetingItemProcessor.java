package com.swabber.batch.user.greeting.chunk;

import com.swabber.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserGreetingItemProcessor implements ItemProcessor<User, String> {
    @Override
    public String process(User user) {
        String greetingMessage = String.format("Hello, %s %s ! You're %s.", user.getLastName(), user.getFirstName(), user.getAge());
        log.info("Execute item processor. processed message : '{}'", greetingMessage);
        return greetingMessage;
    }
}

package com.swabber.batch.user.greeting.chunk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserGreetingItemWriter extends FlatFileItemWriter<String> {

    private static final String FILE_PATH = "./";
    private static final String FILE_NAME = "greetingMessage.txt";

    public UserGreetingItemWriter() {
        setName("userGreetingItemWriter");
        setResource(new FileSystemResource(FILE_PATH + FILE_NAME));
        setLineAggregator(new PassThroughLineAggregator<>());
    }

    @Override
    public String doWrite(final List<? extends String> items) {
        log.info("Execute item writer. size of data : {}", items.size());
        return super.doWrite(items);
    }
}

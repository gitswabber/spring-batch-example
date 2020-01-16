package com.swabber.batch.user.greeting.chunk;

import com.swabber.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
public class UserGreetingItemReader extends FlatFileItemReader<User> {

    private static final String USER_FILE_NAME = "csv/users.csv";
    private static final String[] USER_FILE_CSV_FIELDS = new String[]{"firstName", "lastName", "age"};

    public UserGreetingItemReader() throws Exception {
        setName("userGreetingItemReader");
        setResource(new ClassPathResource(USER_FILE_NAME));
        setEncoding(StandardCharsets.UTF_8.name());
        setLinesToSkip(1);
        setLineMapper(getLineMapper(getDelimitedLineTokenizer()));
        setStrict(true);
        afterPropertiesSet();
    }

    @Override
    protected User doRead() throws Exception {
        final User user = super.doRead();
        if (Objects.nonNull(user)) {
            log.info("Execute item reader. user name : {}", user.getFirstName());
        }
        return user;
    }

    private DelimitedLineTokenizer getDelimitedLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(USER_FILE_CSV_FIELDS);
        return lineTokenizer;
    }

    private DefaultLineMapper<User> getLineMapper(DelimitedLineTokenizer lineTokenizer) {
        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        final BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}

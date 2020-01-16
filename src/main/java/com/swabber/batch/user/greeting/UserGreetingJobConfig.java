package com.swabber.batch.user.greeting;

import com.swabber.batch.user.greeting.chunk.UserGreetingItemProcessor;
import com.swabber.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class UserGreetingJobConfig {

    private final JobBuilderFactory jobBuilder;

    private final StepBuilderFactory stepBuilder;

    public UserGreetingJobConfig(JobBuilderFactory jobBuilder, StepBuilderFactory stepBuilder) {
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
    }

    @Bean
    public Job userGreetingJob(Step userGreetingStep) {
        return jobBuilder.get("userGreetingJob")
                .start(userGreetingStep)
                .build();
    }

    @Bean
    public Step userGreetingStep() {
        return stepBuilder.get("userGreetingStep")
                .<User, String>chunk(3)
                .reader(userGreetingItemReader())
                .processor(userGreetingItemProcessor())
                .writer(userGreetingItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<User> userGreetingItemReader() {
        return new FlatFileItemReaderBuilder<User>()
                .name("userGreetingItemReader")
                .resource(new ClassPathResource("csv/users.csv"))
                .delimited().names(new String[]{"firstName", "lastName", "age"})
                .targetType(User.class).build();
    }

    @Bean
    public UserGreetingItemProcessor userGreetingItemProcessor() {
        return new UserGreetingItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<String> userGreetingItemWriter() {
        return new FlatFileItemWriterBuilder<String>()
                .name("userGreetingItemWriter")
                .resource(new FileSystemResource("greetingMessage.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }
}

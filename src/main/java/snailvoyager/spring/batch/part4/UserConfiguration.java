package snailvoyager.spring.batch.part4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class UserConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomerRepository customerRepository;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job userJob() throws Exception {
        return jobBuilderFactory.get("userJob")
                .incrementer(new RunIdIncrementer())
                .start(this.saveUserStep())
                .next(this.userLevelUpStep())
                .listener(new LevelUpJobExecutionListener(customerRepository))
                .build();
    }

    @Bean
    public Step saveUserStep() {
        return stepBuilderFactory.get("saveUserStep")
                .tasklet(new SaveUserTasklet(customerRepository))
                .build();
    }

    @Bean
    public Step userLevelUpStep() throws Exception {
        return this.stepBuilderFactory.get("userLevelUpStep")
                .<Customer, Customer>chunk(100)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private ItemWriter<? super Customer> itemWriter() {
        return customer -> {
            customer.forEach(x -> {
                x.levelUp();
                customerRepository.save(x);
            });
        };
    }

    private ItemProcessor<? super Customer, ? extends Customer> itemProcessor() {
        return customer -> {
            if (customer.availableLevelUp()) {
                return customer;
            }
            return null;
        };
    }

    private ItemReader<? extends Customer> itemReader() throws Exception {
        JpaPagingItemReader<Customer> itemReader = new JpaPagingItemReaderBuilder<Customer>()
                .queryString("select c from Customer c")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .name("customerItemReader")
                .build();
        itemReader.afterPropertiesSet();

        return itemReader;
    }
}

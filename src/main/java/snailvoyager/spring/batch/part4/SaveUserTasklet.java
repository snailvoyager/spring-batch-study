package snailvoyager.spring.batch.part4;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class SaveUserTasklet implements Tasklet {
    private final CustomerRepository customerRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Customer> customers = createUsers();

        Collections.shuffle(customers);
        customerRepository.saveAll(customers);

        return RepeatStatus.FINISHED;
    }

    private List<Customer> createUsers() {
        List<Customer> customers = new ArrayList<>();

        for (int i = 0; i<100; i++) {
            customers.add(Customer.builder()
                    .totalAmount(1_000)
                    .username("test username" + i)
                    .build());
        }

        for (int i=100; i<200; i++) {
            customers.add(Customer.builder()
                    .totalAmount(200_000)
                    .username("test username" + i)
                    .build());
        }

        for (int i=200; i<300; i++) {
            customers.add(Customer.builder()
                    .totalAmount(300_000)
                    .username("test username" + i)
                    .build());
        }

        for (int i=300; i<400; i++) {
            customers.add(Customer.builder()
                    .totalAmount(500_000)
                    .username("test username" + i)
                    .build());
        }

        return customers;
    }
}

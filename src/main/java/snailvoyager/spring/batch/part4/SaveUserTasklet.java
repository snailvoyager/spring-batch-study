package snailvoyager.spring.batch.part4;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import snailvoyager.spring.batch.part5.Orders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class SaveUserTasklet implements Tasklet {
    private final int SIZE = 100;
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

        for (int i = 0; i<SIZE; i++) {
            customers.add(Customer.builder()
                    .orders(Collections.singletonList(Orders.builder()
                            .amount(1_000)
                            .createdDate(LocalDate.of(2023, 1, 1))
                            .itemName("item" + i)
                            .build()))
                    .username("test username" + i)
                    .build());
        }

        for (int i=0; i<SIZE; i++) {
            customers.add(Customer.builder()
                    .orders(Collections.singletonList(Orders.builder()
                            .amount(200_000)
                            .createdDate(LocalDate.of(2023, 2, 2))
                            .itemName("item" + i)
                            .build()))
                    .username("test username" + i)
                    .build());
        }

        for (int i=0; i<SIZE; i++) {
            customers.add(Customer.builder()
                    .orders(Collections.singletonList(Orders.builder()
                            .amount(300_000)
                            .createdDate(LocalDate.of(2023, 1, 3))
                            .itemName("item" + i)
                            .build()))
                    .username("test username" + i)
                    .build());
        }

        for (int i=0; i<SIZE; i++) {
            customers.add(Customer.builder()
                    .orders(Collections.singletonList(Orders.builder()
                            .amount(500_000)
                            .createdDate(LocalDate.of(2023, 1, 4))
                            .itemName("item" + i)
                            .build()))
                    .username("test username" + i)
                    .build());
        }

        return customers;
    }
}

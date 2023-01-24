package snailvoyager.spring.batch.part4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.time.LocalDate;
import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class LevelUpJobExecutionListener implements JobExecutionListener {
    private final CustomerRepository customerRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Collection<Customer> customers = customerRepository.findAllByUpdatedDate(LocalDate.now());

        long time = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();

        log.info("회원등급 업데이트 배치 프로그램");
        log.info("--------------------------");
        log.info("총 데이터 처리 {}건, 처리 시간 {}millis", customers.size(), time);
    }
}

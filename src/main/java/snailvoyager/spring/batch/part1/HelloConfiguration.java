package snailvoyager.spring.batch.part1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class HelloConfiguration {
    private final JobBuilderFactory jobBuilderFactory;      //job을 만드는 역할
    private final StepBuilderFactory stepBuilderFactory;    //step을 만드는 역할

    public HelloConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) { //spring batch plugin install
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job helloJob() {     //batch 실행 단위
        return jobBuilderFactory.get("helloJob")
                .incrementer(new RunIdIncrementer())        //job을 생성할 때마다 parameterId를 자동 생성해줌, 새로운 job instance 생성 가능
                .start(this.helloStep())       //최초로 실행될 step
                .build();
    }

    @Bean
    public Step helloStep() {   //job 실행 단위
        return stepBuilderFactory.get("helloStep")
                .tasklet((contribution, chunkContext) -> {  //task 기반
                    log.info(">>>>> hello spring batch");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}

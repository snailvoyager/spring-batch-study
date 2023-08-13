package snailvoyager.spring.batch.person.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import snailvoyager.spring.batch.config.DataSourceConfig;
import snailvoyager.spring.batch.config.MyBatisConfig;
import snailvoyager.spring.batch.part3.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@ActiveProfiles("mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //Using a real database
@ContextConfiguration(classes = {MyBatisConfig.class, DataSourceConfig.class})
class PersonMapperTest {

    @Autowired
    PersonMapper personMapper;

    @Test
    void test_selectPersonsByAddress_thenSuccess() {
        List<Person> results = personMapper.selectPersonsByAddress("서울");
        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    void test_insertPerson_thenSuccess() {
        Person person = new Person(19, "UNKOWN", "32", "인천");
        int result = personMapper.insertPerson(person);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void test_insertPerson_whenNameIsNUllThenSuccess() {
        Person person = new Person(19, null, null, "인천");
        int result = personMapper.insertPerson(person);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void test_getAllPersons_whenXmlMapperThenSuccess() {
        List<Person> result = personMapper.getAllPersons();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(12);
    }
}
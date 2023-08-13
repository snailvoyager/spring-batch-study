package snailvoyager.spring.batch.person.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import snailvoyager.spring.batch.part3.Person;

import java.util.List;

@Mapper
public interface PersonMapper {

    @Select("SELECT * FROM PERSON WHERE address = #{address}")
    List<Person> selectPersonsByAddress(String address);

    @Insert("INSERT INTO PERSON VALUES(#{id}, #{address}, #{age}, #{name})")
    int insertPerson(Person person);

    List<Person> getAllPersons();
}

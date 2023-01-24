package snailvoyager.spring.batch.part4;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Collection<Customer> findAllByUpdatedDate(LocalDate updatedDate);
}

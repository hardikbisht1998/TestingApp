package com.hardik.TestingApp.repositories;

import com.hardik.TestingApp.TestContainerConfiguration;
import com.hardik.TestingApp.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
public class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .name("hardik")
                .email("hardibisht98@gmail.com")
                .salary(1000000L).build();

    }

    @Test
    void testFindByEmail_whenEmailIsValid_thenReturnEmployee() {
//        GIven,Arrange
        employeeRepository.deleteAll();
        employeeRepository.save(employee);


//        act,when
        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

//        Assert, Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();


        assertThat(employeeList.getFirst().getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {

        String email = "notPresent@gmail.com";
        //whenl̥
        List<Employee> employeeList = employeeRepository.findByEmail(email);

        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();
    }
}

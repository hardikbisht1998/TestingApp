package com.hardik.TestingApp.repositories;

import com.hardik.TestingApp.TestContainerConfiguration;
import com.hardik.TestingApp.entities.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp(){
        employee=Employee.builder()
                .email("hardik@gmail.com")
                .name("hardik")
                .salary(100L)
                .build();
    }
    @AfterEach
    void setDown(){
        employee=null;
    }



    @Test
    void testFindByEmail_whenEmailsValid_thenReturnEmployee() {
       //Arrange,Given

        employeeRepository.save(employee);
       //Act,WHEN
        List<Employee> employeeList=employeeRepository.findByEmail(employee.getEmail());


//       Assert, Then
        assertThat(employeeList).isEqualTo(Collections.singletonList(employee));
        assertThat(employeeList).isNotNull();
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList(){
    String email="notPresent@gmail.com";

    List<Employee> employeeList=employeeRepository.findByEmail(email);

    assertThat(employeeList).isNotNull();
    assertThat(employeeList).isEmpty();
    }
}
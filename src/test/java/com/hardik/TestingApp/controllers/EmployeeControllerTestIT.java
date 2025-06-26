package com.hardik.TestingApp.controllers;

import com.hardik.TestingApp.TestContainerConfiguration;
import com.hardik.TestingApp.dto.EmployeeDto;
import com.hardik.TestingApp.entities.Employee;
import com.hardik.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;


class EmployeeControllerTestIT extends AbstractControllerIt {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp(){
        employee=Employee.builder().email("hardik@gmail.com").name("hardik").salary(10000L).build();
        employeeDto=EmployeeDto.builder().email("hardik@gmail.com").name("hardik").salary(10000L).build();
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_success(){
        Employee savedEmployee=employeeRepository.save(employee);
        employeeDto.setId(1L);
        webTestClient.get().uri("/employees/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(employeeDto)
                .value(employeeDto1 -> {
                    assertThat(employeeDto1.getEmail()).isEqualTo(employeeDto.getEmail());
                    assertThat(employeeDto1.getSalary()).isEqualTo(employeeDto.getSalary());
                });
    }

    @Test
    void testGetEmployeeById_failure(){
        webTestClient.get().uri("/employees/{id}",2)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNewEmployee_WhenEmployeeAlreadyExist_thenThrowException(){
        Employee savedEmployee=employeeRepository.save(employee);
        webTestClient.post().uri("/employees").bodyValue(employeeDto)
                .exchange().expectStatus().is5xxServerError();

    }

    @Test
    void testCreateNewEmployee_WhenEmployeeNotExist_thenCreated(){

        webTestClient.post().uri("/employees").bodyValue(employeeDto)
                .exchange().expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo("hardik@gmail.com")
                .jsonPath("$.salary").isEqualTo(employeeDto.getSalary());

    }

}
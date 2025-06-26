package com.hardik.TestingApp.services.impl;

import com.hardik.TestingApp.dto.EmployeeDto;
import com.hardik.TestingApp.entities.Employee;
import com.hardik.TestingApp.exceptions.ResourceNotFoundException;
import com.hardik.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@Import(TestContainerConfiguration.class)
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

//    @Mock
    @Spy
    private ModelMapper modelMapper;

    @Test
    void getEmployeeById() {
        when(employeeRepository.findById(1l)).thenReturn(Optional.ofNullable(Employee.builder().email("111").build()));
        EmployeeDto expectedEmployeeDto=EmployeeDto.builder().email("111").build();
//        when(modelMapper.map(any(),any())).thenReturn(expectedEmployeeDto);
        EmployeeDto employeeDto=employeeService.getEmployeeById(1l);
        assertEquals(employeeDto,expectedEmployeeDto);

        verify(employeeRepository,only()).findById(1l);
//        verify(employeeRepository).save(null);

    }

    @Test
    void testGetEmployeeById_whenEmployeeIsNotPresent_ThrowException(){
            when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

            assertThatThrownBy(()->employeeService.getEmployeeById(1L)).isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Employee not found with id: 1");

            verify(employeeRepository).findById(1l);
    }

    @Test
    void createNewEmployee() {

        when(employeeRepository.findByEmail(any())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(Employee.builder().id(1l).build());
        EmployeeDto employeeDto=employeeService.createNewEmployee(EmployeeDto.builder().id(1l).build());

        ArgumentCaptor<Employee> employeeArgumentCaptor=ArgumentCaptor.forClass(Employee.class);

        assertThat(employeeDto).isNotNull();
        assertEquals(employeeDto,EmployeeDto.builder().id(1l).build());
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee captures=employeeArgumentCaptor.getValue();
        assertThat(captures.getId()).isEqualTo(1l);
    }

    @Test
    void testCreateNewEmployee_whenUserExist_thenThrowException(){

        when(employeeRepository.findByEmail(any())).thenReturn(Collections.singletonList(Employee.builder().id(1l).build()));

//        employeeService.createNewEmployee(EmployeeDto.builder().id(1l).build());

        assertThatThrownBy(()-> employeeService.createNewEmployee(EmployeeDto.builder().id(1l).build()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: null");

        verify(employeeRepository).findByEmail(any());
        verify(employeeRepository,never()).save(any());


    }

    @Test
    void updateEmployee_when() {
    }

    @Test
    void deleteEmployee() {
    }
}
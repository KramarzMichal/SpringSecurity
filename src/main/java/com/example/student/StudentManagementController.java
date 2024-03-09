package com.example.student;

import com.example.student.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "James Bond"),
            new Student(2, "Karol Bond"),
            new Student(3, "Adam Bond")
    );
    // Parametry dla adnotacji @PreAuthorize()
    // "hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')"
    // "hasRole('ROLE_ADMIN')",
    // "hasAuthority('student:write')"
    // "hasAnyAuthority('student:write', 'student:read)"
    @GetMapping // czasownik HTTP
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents(){
        System.out.println("getAllStudents");
        return STUDENTS;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void addNewStudent(@RequestBody Student student){
        System.out.println("addNewStudent");
        System.out.println(student);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable Integer id){
        System.out.println("deleteStudent");
        System.out.println(id);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable Integer id, @RequestBody Student student){
        System.out.println("updateStudent");
        System.out.printf("%s %s", id, student);
    }

}

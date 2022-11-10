package com.example.aws.controller;

import com.example.aws.model.Student;
import com.example.aws.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class TrialController {

    private StudentRepository studentRepository;

    @Autowired
    public TrialController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/time")
    public String getTimeMessage() {
        return "The current time is [" + Instant.now() + "].";
    }

    @PostMapping("/json")
    public String getJson(@RequestBody String csv) {
        String[] all = csv.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int length = all.length;
        for(int i = 1; i < length; i++) {
            addToJSON(all, sb, i);
            sb.append(",");
        }
        addToJSON(all, sb, length);
        sb.append("}");
        return sb.toString();
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/student")
    public String addStudent() {
        Student student = new Student("Student" + ThreadLocalRandom.current().nextInt(), "Name" + ThreadLocalRandom.current().nextInt(), ThreadLocalRandom.current().nextInt());
        studentRepository.save(student);
        return "OK:" + student;
    }

    private static void addToJSON(String[] all, StringBuilder sb, int i) {
        sb.append("\"var");
        sb.append(i);
        sb.append("\":");
        appendValue(all, sb, i);
    }

    private static void appendValue(String[] all, StringBuilder sb, int i) {
        sb.append("\"");
        sb.append(all[i -1]);
        sb.append("\"");
    }

}

package academia.controllers;

import academia.domain.student.Student;
import academia.domain.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class StudentWebController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    private static final String API_BASE_URL = "http://localhost:8081/api";

    @GetMapping
    public String getAllStudents(Model model) {
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                API_BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {}
        );
        List<Student> students = response.getBody();
        if (students != null) {
            model.addAttribute("index", students);
        } else {
            model.addAttribute("index", Collections.emptyList());
        }

        return "index";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "form";
    }

    @PostMapping("/form")
    public String addStudent(@ModelAttribute Student student, Model model) {
        try {
            String url = API_BASE_URL ;

            restTemplate.postForEntity(url, student, Student.class);
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao adicionar o estudante: " + e.getMessage());
            return "form"; //
        }

        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") String id, Model model) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            model.addAttribute("student", student);
        }
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") String id, @ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        student.setId(id);
        studentRepository.save(student);
        redirectAttributes.addFlashAttribute("message", "Aluno atualizado com sucesso!");
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Aluno deletado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Aluno não encontrado!");
        }
        return "redirect:/";


    }

    @GetMapping("/search")
    public String searchStudent(@RequestParam("name") String name, Model model){
        String url = API_BASE_URL + "/search?name=" + name;
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {}
        );
        List<Student> students = response.getBody();
        model.addAttribute("students", students != null ? students : Collections.emptyList());
        return "searchByName";
    }
}

package com.irproject.restapp;

import com.irproject.entities.BagOfWords;
import com.irproject.entities.User;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

//import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
//import net.guides.springboot2.springboot2jpacrudexample.model.Employee;
//import net.guides.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private static BagOfWords bagOfWords = new BagOfWords();
    private static List<User> userList = bagOfWords.setUsersInterests();;

//
//    public UserController (){
//        bagOfWords = new BagOfWords();
//        bagOfWords.createUsersBagOfWords();
//        userList = bagOfWords.setUsersInterests();
//    }

    @GetMapping("/users")
    public JSONArray getAllUsers() {
        System.out.println("torna lista utenti");
        JSONArray usersarray = new JSONArray();
        Iterator<User> iterator = userList.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();
            JSONObject userjson = new JSONObject();
            userjson.put("name", user.getName());
            userjson.put("topicalInterests", user.getUserInterests());
            userjson.put("id", user.getId());
            userjson.put("imagePath", user.getImagePath());
            usersarray.add(userjson);
        }
        return usersarray;
    }
    // UserInterest topicalInterest, String queryString, boolean personalizedSearch, boolean searchByUser, boolean searchByHashtag
    @GetMapping("/users/{id}/")
    public User getUserByName(@PathVariable(value = "id") int id) {

        User results = userList.get(id);

        return results;
    }


   /* @GetMapping("/search/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }

   */








}

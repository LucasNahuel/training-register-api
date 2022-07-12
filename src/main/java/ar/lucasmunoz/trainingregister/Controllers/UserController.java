package ar.lucasmunoz.trainingregister.Controllers;


import ar.lucasmunoz.trainingregister.Models.User;
import ar.lucasmunoz.trainingregister.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Transactional
    @PostMapping("updateUser")
    public @ResponseBody ResponseEntity<?> updateUser(@RequestParam("id") Integer id, @RequestParam("userName") String userName,
                                                    @RequestParam("password") String password, @RequestParam("email") String email){

        User u = userRepository.findById(id).get();

        u.setUser(userName);
        u.setPassword(password);
        u.setEmail(email);

        userRepository.save(u);



        return new ResponseEntity<>("{\"message\":\"Changes saved\"}", HttpStatus.OK);



    }


    @Transactional
    @GetMapping("getUserByName/{userName}")
    public @ResponseBody ResponseEntity<?> getUserByName(@PathVariable String userName){


        User u = null;

        if(this.userRepository.findByUser(userName).size()>0){
           u = this.userRepository.findByUser(userName).get(0);
        }




        return new ResponseEntity<>(u, HttpStatus.OK);

    }

    @Transactional
    @GetMapping("isUsernameTaken")
    public @ResponseBody ResponseEntity<?> isUsernameTaken(@RequestParam("userName") String userName, @RequestParam("id") Integer id){

        System.out.println("this line");

        List<User> lu = userRepository.findByUser(userName);

        if(lu.size() > 0){
            if(lu.get(0).getId() != id){
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>( null, HttpStatus.OK);



    }



}

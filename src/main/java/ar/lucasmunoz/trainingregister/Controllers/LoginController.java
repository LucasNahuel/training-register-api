package ar.lucasmunoz.trainingregister.Controllers;


import ar.lucasmunoz.trainingregister.Models.ResponseMessage;
import ar.lucasmunoz.trainingregister.Models.User;
import ar.lucasmunoz.trainingregister.Repositories.*;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {


    //this makes spring generate a userRepository to manage the data for the users
    @Autowired
    private UserRepository userRepository;


    @PostMapping("login")
    public @ResponseBody Object login(@RequestParam("user") String user, @RequestParam("password") String password) {


        if( userRepository.findByUserAndPassword(user, password).isEmpty() ){
            return new ResponseMessage("invalid credentials");
        }else{
            return new ResponseMessage(getJWTToken(user));
        }


    }

    @PostMapping("register")
    public @ResponseBody Object
    register(@RequestParam("User") String user, @RequestParam("password") String password,
             @RequestParam("Email") String email){

        //this returns a error if the username has already been registered
        if(userRepository.findByUser(user).isEmpty() == false) {
            return new ResponseMessage("username used");
        }else{
            User newUser = new User();
            newUser.setUser(user);
            newUser.setPassword(password);
            newUser.setEmail(email);

            userRepository.save(newUser);

            return new ResponseMessage("registered successfully");
        }



    }

    @PostMapping("/home")
    public @ResponseBody String home(){
        return "this is the home";
    }



    private String getJWTToken(String username) {
        String secretKey = "LucasMunozSecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("LucasMunozJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3000000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }


}

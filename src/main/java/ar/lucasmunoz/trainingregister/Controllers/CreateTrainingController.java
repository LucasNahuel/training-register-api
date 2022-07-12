package ar.lucasmunoz.trainingregister.Controllers;


import ar.lucasmunoz.trainingregister.Models.*;
import ar.lucasmunoz.trainingregister.Repositories.*;
import ar.lucasmunoz.trainingregister.Services.TrainingService;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@RestController
public class CreateTrainingController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TrainingDayRepository trainingDayRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseDayRepository exerciseDayRepository;
    @Autowired
    private ExerciseSetRepository exerciseSetRepository;
    @Autowired
    private TrainingService trainingService;





    @PostMapping("createTraining")
    public @ResponseBody ResponseEntity<?> createTraining(@RequestParam String userCreatorName, @RequestParam String trainingName, @RequestParam JSONArray JSONexerciceList) throws SQLException {


        //first, check if the same user already create a training whit the same name


        System.out.print(trainingName +" "+ userCreatorName +" "+ JSONexerciceList);
        return trainingService.createTraining(userCreatorName, trainingName, JSONexerciceList);


    }

}

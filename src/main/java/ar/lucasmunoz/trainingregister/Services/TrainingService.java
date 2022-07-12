package ar.lucasmunoz.trainingregister.Services;


import ar.lucasmunoz.trainingregister.Models.*;
import ar.lucasmunoz.trainingregister.Repositories.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import java.sql.SQLException;

@Service
public class TrainingService {

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
    private TrainingOfUsersRepository trainingOfUsersRepository;


    @Transactional
    public ResponseEntity<?> createTraining(String userCreatorName, String trainingName, JSONArray JSONexerciceList) throws SQLException {

        if (trainingRepository.findByNameAndCreatorFK(trainingName, userRepository.findByUser(userCreatorName).get(0)).size() == 0){


            Training training = new Training();
            training.setName(trainingName);
            training.setCreatorFK(userRepository.findByUser(userCreatorName).get(0));
            Training trainingSaved = trainingRepository.save(training);


            TrainingOfUsers tou = new TrainingOfUsers();
            tou.setTraining(trainingSaved);
            tou.setActive(true);
            tou.setUser(userRepository.findByUser(userCreatorName).get(0));
            trainingOfUsersRepository.save(tou);


            //iterates trough the exercise list and save them
            for (int i = 0; i < JSONexerciceList.length(); i++) {

                JSONObject item = (JSONObject) JSONexerciceList.get(i);

                Exercise e = new Exercise();

                e.setName(item.get("name").toString());

                e.setTrainingFk(trainingSaved);

                Exercise exerciseSaved = exerciseRepository.save(e);

                ExerciseDay exerciseDay = new ExerciseDay();


                exerciseDay.setTrainingDayFk(trainingDayRepository.findByDay(item.get("day").toString()).get(0));

                exerciseDay.setExerciseFk(exerciseSaved);

                exerciseDay.setExecutionOrder(i);

                ExerciseDay savedExerciseDay = exerciseDayRepository.save(exerciseDay);

                JSONArray JSONsetsList = new JSONArray(item.get("sets").toString()) ;

                for(int j = 0; j< JSONsetsList.length(); j++){

                    JSONObject jsonSet = (JSONObject) JSONsetsList.get(j);

                    ExerciseSet set = new ExerciseSet();

                    set.setRepetitions((int)jsonSet.get("reps"));

                    set.setWeight(Float.parseFloat(jsonSet.get("weight").toString()));

                    set.setExerciseDayFk(savedExerciseDay);

                    exerciseSetRepository.save(set);

                }
            }

        }else{
            return new ResponseEntity<>("{\"message\":\"You already created a training with that name\"}", HttpStatus.OK);
        }

        return new ResponseEntity<>("{\"message\":\"Correctly saved\"}", HttpStatus.OK);
}

}

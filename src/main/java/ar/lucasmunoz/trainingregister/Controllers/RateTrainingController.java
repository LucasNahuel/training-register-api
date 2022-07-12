package ar.lucasmunoz.trainingregister.Controllers;


import ar.lucasmunoz.trainingregister.Models.Training;
import ar.lucasmunoz.trainingregister.Models.TrainingRatings;
import ar.lucasmunoz.trainingregister.Models.User;
import ar.lucasmunoz.trainingregister.Repositories.TrainingRatingRepository;
import ar.lucasmunoz.trainingregister.Repositories.TrainingRepository;
import ar.lucasmunoz.trainingregister.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RateTrainingController {

    @Autowired
    TrainingRatingRepository trainingRatingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrainingRepository trainingRepository;


    @Transactional
    @PostMapping("rateTraining")
    public @ResponseBody ResponseEntity<?> rateTraining(@RequestParam Integer trainingFK, @RequestParam String userName, @RequestParam Integer rate, @RequestParam String comment){

        User u = userRepository.findByUser(userName).get(0);

        Training t = trainingRepository.findById(trainingFK).get();



        if(trainingRatingRepository.findAllByTrainingFKAndUserFK(t, u).size() > 0){

            TrainingRatings tr = trainingRatingRepository.findAllByTrainingFKAndUserFK(t, u).get(0);

            tr.setRating(rate);
            tr.setComment(comment);

            trainingRatingRepository.save(tr);

        }else{

            TrainingRatings tr = new TrainingRatings();
            tr.setTrainingFK(t);
            tr.setUserFK(u);
            tr.setRating(rate);
            tr.setComment(comment);

            trainingRatingRepository.save(tr);


        }





        return new ResponseEntity<>("{\"message\":\"Correctly saved\"}", HttpStatus.OK);
    }


    @PostMapping("getRatingsPage")
    public @ResponseBody ResponseEntity<?> getRatingsPage(@RequestParam Integer trainingFK, @RequestParam Integer pageNo){

        Pageable paging = PageRequest.of(pageNo,20);

        Training t = trainingRepository.findById(trainingFK).get();

        List<TrainingRatings> ltr = trainingRatingRepository.findAllByTrainingFK(t, paging);

        return new ResponseEntity<>(ltr, HttpStatus.OK);
    }

    @PostMapping("getRatingByUserAndTraining")
    public @ResponseBody ResponseEntity<?> getRatingByUserAndTraining(@RequestParam String userName, @RequestParam Integer trainingFK){

        User u = userRepository.findByUser(userName).get(0);

        Training t = trainingRepository.findById(trainingFK).get();

        List<TrainingRatings> ltr = trainingRatingRepository.findAllByTrainingFKAndUserFK(t, u);

        if(ltr.size() == 0){
            return new ResponseEntity<>( null, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(ltr.get(0), HttpStatus.OK);
        }


    }

    @PostMapping("deleteRating")
    public @ResponseBody ResponseEntity<?> deleteRating(@RequestParam Integer ratingId){

        TrainingRatings tr = trainingRatingRepository.findById(ratingId).get();

        trainingRatingRepository.delete(tr);


        return new ResponseEntity<>("{\"message\":\"correctly deleted\"}", HttpStatus.OK);
    }


}

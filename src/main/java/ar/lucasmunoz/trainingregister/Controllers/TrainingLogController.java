package ar.lucasmunoz.trainingregister.Controllers;

import ar.lucasmunoz.trainingregister.Models.*;
import ar.lucasmunoz.trainingregister.Repositories.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class TrainingLogController {


    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private TrainingOfUsersRepository trainingOfUsersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainingDayRepository trainingDayRepository;

    @Autowired
    private ExerciseDayRepository exerciseDayRepository;

    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private TrainingLogRepository trainingLogRepository;

    @Autowired
    private SetLogRepository setLogRepository;


    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingPagingRepository trainingPagingRepository;

    @Autowired
    private TrainingRatingRepository trainingRatingRepository;

    @Transactional
    @GetMapping("getTraining")
    public @ResponseBody ResponseEntity<?> getTraining(@RequestParam("user") String user, @RequestParam("date") String date){



        Calendar cal = Calendar.getInstance();

        int year = Integer.parseInt(date.split("-")[2]);

        int month = Integer.parseInt(date.split("-")[1]);

        int day = Integer.parseInt(date.split("-")[0]);

        cal.set(year,month-1,day,00,00,00);

        Date formatedDate = cal.getTime();



        long time = formatedDate.getTime();
        formatedDate.setTime((time / 1000) * 1000);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEEE", Locale.ENGLISH);

        List<TrainingOfUsers> trainingOfUsersList = trainingOfUsersRepository.findByUserAndIsActive(userRepository.findByUser(user).get(0), true);

        List<TrainingLogResponse> trainingLogResponseList = new ArrayList<TrainingLogResponse>();

        for (int i = 0; i< trainingOfUsersList.size(); i++){

            TrainingLogResponse tlr = new TrainingLogResponse();

            tlr.setTrainingName(trainingOfUsersList.get(i).getTraining().getName());



            List<Exercise> exerciseList = exerciseRepository.findByTrainingFk(trainingOfUsersList.get(i).getTraining());

            for(int j = 0; j<exerciseList.size(); j++){



                TrainingDay td = trainingDayRepository.findByDay(sdf.format(formatedDate).toLowerCase(Locale.ENGLISH)).get(0);

                List<ExerciseDay> listOfExerciseDay = exerciseDayRepository.findByExerciseFkAndTrainingDayFk(exerciseList.get(j), td);


                //case you don't have any exercise to do that given day
                if(listOfExerciseDay.size()>0){
                    ExerciseLogResponse elr = new ExerciseLogResponse();

                    elr.setExerciseName(listOfExerciseDay.get(0).getExerciseFk().getName());

                    List<ExerciseSet> exerciseSetsList = exerciseSetRepository.findByExerciseDayFk(listOfExerciseDay.get(0));


                    for (int k = 0; k<exerciseSetsList.size(); k++){


                        SetResponse sr = new SetResponse();
                        sr.setReps(exerciseSetsList.get(k).getRepetitions());
                        sr.setWeight(exerciseSetsList.get(k).getWeight());
                        sr.setSetId(exerciseSetsList.get(k).getId());


                        List<TrainingLog> trainingLogList= new ArrayList<TrainingLog>();
                        List<TrainingLog> trainingLogList2 = trainingLogRepository.findByUserFK(userRepository.findByUser(user).get(0));




                        for(int l = 0; l<trainingLogList2.size(); l++){

                            SimpleDateFormat sdf2 = new SimpleDateFormat();

                            String dateFromDb = sdf2.format(trainingLogList2.get(l).getDate());
                            String dateFormated = sdf2.format(formatedDate);

                            int dayFromdb = Integer.parseInt(dateFromDb.split("/")[0]);
                            int monthFromdb = Integer.parseInt(dateFromDb.split("/")[1]);
                            int yearFromdb = Integer.parseInt("20"+dateFromDb.split("/")[2].substring(0,2));



                            if(dayFromdb == day && monthFromdb == month && yearFromdb == year){
                                trainingLogList.add(trainingLogList2.get(l));
                            }
                        }





                        if(trainingLogList.size() > 0){
                            TrainingLog tl = trainingLogList.get(0);


                            List<SetLog> setLogList = setLogRepository.findByTrainingLogFKAndSetFK(tl, exerciseSetsList.get(k));

                            if(setLogList.size() > 0){
                                SetLog sl = setLogList.get(0);

                                sr.setRepsLogged(sl.getRepetitionsDone());
                                sr.setWeightLogged(sl.getWeightUsed());
                            }

                        }

                        elr.addSet(sr);


                    }

                    if(elr != null){

                        tlr.addExercise(elr);

                    }

                }


            }


            if(tlr.getExercises().size() > 0){
                trainingLogResponseList.add(tlr);
            }


        }


        return new ResponseEntity<Object>(trainingLogResponseList, HttpStatus.OK);


    }

    @Transactional
    @PostMapping("saveSet")
    public @ResponseBody ResponseEntity<?> saveSet(@RequestParam("user") String user, @RequestParam("setFk") int setFk, @RequestParam("date") String date,
                                                   @RequestParam("reps") int repsDone, @RequestParam("weight") float weight){

        //1 find the user

        User userModel = userRepository.findByUser(user).get(0);

        Calendar cal = Calendar.getInstance();

        int year = Integer.parseInt(date.split("-")[2]);

        int month = Integer.parseInt(date.split("-")[1]);

        int day = Integer.parseInt(date.split("-")[0]);

        cal.set(year,month-1,day, 00,00,00);

        Date formattedDate = cal.getTime();

        long time = formattedDate.getTime();
        formattedDate.setTime((time / 1000) * 1000);

        //2 check if there's a trainingLog for that day already, if not, create it

        TrainingLog tl;

        if(trainingLogRepository.findByUserFKAndDate(userModel, formattedDate).size() == 0){
            //training doesn't exist, create it
            TrainingLog newTrainingLog = new TrainingLog();


            newTrainingLog.setDate(formattedDate);

            newTrainingLog.setUserFK(userModel);

            tl = trainingLogRepository.save(newTrainingLog);


        }else{
            tl = trainingLogRepository.findByUserFKAndDate(userModel, formattedDate).get(0);
        }




        SetLog sl;

        if( setLogRepository.findByTrainingLogFKAndSetFK(tl, exerciseSetRepository.findById(setFk).get()).size() == 0 ){

            sl= new SetLog();

            sl.setSetFK(exerciseSetRepository.findById(setFk).get());

            sl.setTrainingLogFK(tl);

            sl.setRepetitionsDone(repsDone);

            sl.setWeightUsed(weight);


            setLogRepository.save(sl);

        }else{
            sl = setLogRepository.findByTrainingLogFKAndSetFK(tl, exerciseSetRepository.findById(setFk).get()).get(0);
            sl.setRepetitionsDone(repsDone);
            sl.setWeightUsed(weight);
            setLogRepository.save(sl);

        }

        return new ResponseEntity<>("{\"message\":\"Correctly saved\"}", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("getTrainingsByUser")
    public @ResponseBody ResponseEntity<?> getTrainingsByUser(@RequestParam("user") String user){


        User u = userRepository.findByUser(user).get(0);

        List<TrainingOfUsers> tou = trainingOfUsersRepository.findByUserAndIsActive(u, true);

        List<Optional<Training>> trainingsList = new ArrayList<>();

        for(int i = 0; i < tou.size(); i++){

            trainingsList.add(trainingRepository.findById(tou.get(i).getTraining().getId()));

        }



        return new ResponseEntity<>(trainingsList, HttpStatus.OK);

    }

    @Transactional
    @PostMapping("deleteTraining")
    public @ResponseBody ResponseEntity<?> deleteTraining(@RequestParam("trainingId") Integer trainingId, @RequestParam("user") String user){



        Training t = trainingRepository.findById(trainingId).get();

        User u = userRepository.findByUser(user).get(0);

        TrainingOfUsers tou = trainingOfUsersRepository.findByUserAndTraining(u, t).get(0);

        tou.setActive(false);


        trainingOfUsersRepository.save(tou);

        return new ResponseEntity<>("correctly deleted", HttpStatus.OK);

    }



    @Transactional
    @PostMapping("getTrainingAndExercices")
    public @ResponseBody ResponseEntity<?> getTrainingAndExercices(@RequestParam("user") String user){

        User u = userRepository.findByUser(user).get(0);


        List<TrainingOfUsers> listTou = trainingOfUsersRepository.findByUserAndIsActive(u, true);

        List<TrainingLogResponse> listTlr = new ArrayList<TrainingLogResponse>();



        for(int i = 0; i< listTou.size(); i++){

            Training t = listTou.get(i).getTraining();

            TrainingLogResponse tlr = new TrainingLogResponse();

            tlr.setTrainingName(t.getName());
            tlr.setId(t.getId());


            List<Exercise> listExercises = exerciseRepository.findByTrainingFk(t);

            for(int j = 0; j < listExercises.size(); j++){

                ExerciseLogResponse elr = new ExerciseLogResponse();

                elr.setId(listExercises.get(j).getId());
                elr.setExerciseName(listExercises.get(j).getName());

                tlr.addExercise(elr);
            }

            listTlr.add(tlr);
        }




        return new ResponseEntity<>(listTlr, HttpStatus.OK);
    }



    @Transactional
    @PostMapping("getAnalytics")
    public @ResponseBody ResponseEntity<?> getAnalytics(@RequestParam("user") String user, @RequestParam("exerciseId") Integer exerciseId, @RequestParam("dateBegin") String dateBegin,
                                                        @RequestParam("dateEnd") String dateEnd){

        User u = userRepository.findByUser(user).get(0);

        Calendar cal = Calendar.getInstance();

        int yearBegin = Integer.parseInt(dateBegin.split("-")[2]);

        int monthBegin = Integer.parseInt(dateBegin.split("-")[1]);

        int dayBegin = Integer.parseInt(dateBegin.split("-")[0]);

        cal.set(yearBegin,monthBegin-1,dayBegin, 00,00,00);

        Date formattedDateBegin = cal.getTime();

        int yearEnd = Integer.parseInt(dateEnd.split("-")[2]);

        int monthEnd = Integer.parseInt(dateEnd.split("-")[1]);

        int dayEnd = Integer.parseInt(dateEnd.split("-")[0]);

        cal.set(yearEnd,monthEnd-1,dayEnd, 00,00,00);

        Date formattedDateEnd = cal.getTime();



        List<TrainingLog> listTou= trainingLogRepository.findByUserFKAndDateBetween(u, formattedDateBegin, formattedDateEnd);


        Exercise e = exerciseRepository.findById(exerciseId).get();


        List<ExerciseDay> listExerciseDay = exerciseDayRepository.findByExerciseFk(e);

        List<ExerciseSet> listExerciseSet = new ArrayList<ExerciseSet>();


        for(int i = 0; i < listExerciseDay.size(); i++){

            listExerciseSet.addAll(exerciseSetRepository.findByExerciseDayFk(listExerciseDay.get(i)));

        }


        List<SetLog> listSetLog = new ArrayList<>();


        for(int i = 0 ; i < listTou.size(); i++){

            listSetLog.addAll(setLogRepository.findByTrainingLogFK(listTou.get(i)));

        }


        List<SetLog> filteredListSetLog = new ArrayList<>();

        for( int i = 0; i < listSetLog.size(); i++){

            if(listExerciseSet.contains(listSetLog.get(i).getSetFK())){
                filteredListSetLog.add(listSetLog.get(i));
            }

        }

        return new ResponseEntity<>(filteredListSetLog, HttpStatus.OK);


    }

    @Transactional
    @PostMapping("getTrainingsByName")
    public @ResponseBody ResponseEntity<?> getTrainingByName(String name, Integer page){

        Pageable paging = PageRequest.of(page,20);

        List<Training> foundTrainings = trainingPagingRepository.findAllByNameContainingIgnoreCase(name, paging);

        float rating = 0;

        for(int i = 0 ; i< foundTrainings.size(); i++){

            List<TrainingRatings> listTrainingRatings = trainingRatingRepository.findAllByTrainingFK(foundTrainings.get(i));

            foundTrainings.get(i).setNoOfRatings(listTrainingRatings.size());



            for(int j = 0 ; j < listTrainingRatings.size(); j++){

                rating += listTrainingRatings.get(j).getRating();

            }

            rating = rating/listTrainingRatings.size();

            foundTrainings.get(i).setRating(rating);

        }



        return new ResponseEntity<>( foundTrainings, HttpStatus.OK);


    }


    @Transactional
    @PostMapping("getTrainingsById")
    public @ResponseBody ResponseEntity<?> getTrainingById(Integer id){

        Training t = trainingRepository.findById(id).get();


        TrainingLogResponse tlr = new TrainingLogResponse();
        tlr.setId(t.getId());
        tlr.setTrainingName(t.getName());
        tlr.setCreatorName(t.getCreatorFK().getUser());


        List<Exercise> exercises = exerciseRepository.findByTrainingFk(t);

        for(int i = 0 ; i < exercises.size(); i++){

            ExerciseLogResponse elr = new ExerciseLogResponse();
            elr.setExerciseName(exercises.get(i).getName());

            List<ExerciseDay> led = exerciseDayRepository.findByExerciseFk(exercises.get(i));


            for(int j = 0; j < led.size(); j++){

                List<ExerciseSet> les = exerciseSetRepository.findByExerciseDayFk(led.get(j));


                for(int k = 0; k < les.size(); k++){

                    SetResponse sr = new SetResponse();
                    sr.setReps(les.get(k).getRepetitions());
                    sr.setWeight(les.get(k).getWeight());
                    sr.setDayName(les.get(k).getExerciseDayFk().getTrainingDayFk().getDay());
                    elr.addSet(sr);

                }


            }

            tlr.addExercise(elr);
        }



        return new ResponseEntity<>(tlr, HttpStatus.OK);
    }



    @Transactional
    @PostMapping("setTrainingActive")
    public @ResponseBody ResponseEntity<?> setTrainingActive(Integer id, String user){


        User u = userRepository.findByUser(user).get(0);

        Training t = trainingRepository.findById(id).get();



        List<TrainingOfUsers> listtou = trainingOfUsersRepository.findByUserAndTraining(u,t);

        if(listtou.size() == 0) {

            TrainingOfUsers tou = new TrainingOfUsers();

            tou.setTraining(t);
            tou.setUser(u);
            tou.setActive(true);

            trainingOfUsersRepository.save(tou);


            return new ResponseEntity<>("{\"message\":\"Correctly saved\"}", HttpStatus.OK);
        }else{

            if(!listtou.get(0).isActive()){

                listtou.get(0).setActive(true);

                trainingOfUsersRepository.save(listtou.get(0));

                return new ResponseEntity<>("{\"message\":\"Correctly saved\"}", HttpStatus.OK);

            }

            return new ResponseEntity<>( "{\"message\":\"You already have this training active\"}", HttpStatus.OK);
        }


    }


}
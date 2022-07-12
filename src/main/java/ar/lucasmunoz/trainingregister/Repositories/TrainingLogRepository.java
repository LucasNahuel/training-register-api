package ar.lucasmunoz.trainingregister.Repositories;


import ar.lucasmunoz.trainingregister.Models.TrainingLog;
import ar.lucasmunoz.trainingregister.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Repository
public interface TrainingLogRepository extends CrudRepository<TrainingLog, Integer> {

    List<TrainingLog> findByUserFKAndDate(User user, Date date);

    List<TrainingLog> findByUserFK(User user);


    List<TrainingLog> findByUserFKAndDateBetween(User user, Date begin, Date end);




}

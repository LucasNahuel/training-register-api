package ar.lucasmunoz.trainingregister.Repositories;

import ar.lucasmunoz.trainingregister.Models.Training;
import ar.lucasmunoz.trainingregister.Models.TrainingRatings;
import ar.lucasmunoz.trainingregister.Models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TrainingRatingRepository extends PagingAndSortingRepository<TrainingRatings, Integer> {

    List<TrainingRatings> findAllByTrainingFK(Training trainingFK);

    List<TrainingRatings> findAllByTrainingFKAndUserFK(Training trainingFK, User userFK);


    List<TrainingRatings> findAllByTrainingFK(Training trainingFK, Pageable pageable);






}

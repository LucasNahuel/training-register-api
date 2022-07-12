package ar.lucasmunoz.trainingregister.Repositories;

import ar.lucasmunoz.trainingregister.Models.Training;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;




@Repository

public interface TrainingPagingRepository extends PagingAndSortingRepository<Training, Integer> {

        List<Training> findAllByNameContainingIgnoreCase(String name, Pageable pageable);


}

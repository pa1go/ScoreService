package com.gameapp.scorecc.dbaccessors;

import com.gameapp.scorecc.model.UserScore;
import com.gameapp.scorecc.model.UserScoreDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserScoreRepository extends CrudRepository<UserScore, Integer> {

    int PAGE_START_INDEX = 0;
    int PAGE_END_INDEX = 5;

    @Query("SELECT new com.gameapp.scorecc.model.UserScoreDTO(us.scoreValue, us.scoreDate, u.name, u.email, u.userName) " +
            "FROM UserScore us JOIN us.user u ORDER BY us.scoreValue DESC")
    List<UserScoreDTO> findTopScores(Pageable pageable);

    @Query("SELECT new com.gameapp.scorecc.model.UserScoreDTO(us.scoreValue, us.scoreDate, u.name, u.email, u.userName) " +
            "FROM UserScore us JOIN us.user u WHERE u.id = :userId")
    List<UserScoreDTO> findScoresByUserId(@Param("userId") Integer userId);

    default List<UserScoreDTO> findTop5Scores() {
        return findTopScores(PageRequest.of(PAGE_START_INDEX, PAGE_END_INDEX));
    }

}
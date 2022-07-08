package com.example.myHorseServer.repository;

import com.example.myHorseServer.model.EventResult;
import com.example.myHorseServer.model.EventWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface EventResultRepositoryWinner extends JpaRepository<EventResult,Integer> {

    @Query(value="SELECT `points_scored` as `pointsScored`,  `event_id` as `eventId`,  `horse`.`horse_id` as `horseId` FROM `event` INNER JOIN" +
            " `event_type` ON `event_type`.`event_type_id` = `event`.`event_type` INNER JOIN" +
            " `event_list` ON `event`.`event_id` = `event_list`.`event` INNER JOIN" +
            " `horse` ON `event_list`.`horse_id` = `horse`.`horse_id` WHERE `date` < NOW()" +
            " AND event_id = :eventId order by IF(event_type = 1,`horse`.`fast`, `horse`.`appearance`) desc limit 1", nativeQuery=true)
    List<EventWinner> getWinnersOfEvent(int eventId);

}

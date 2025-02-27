package Repository;

import TableDao.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;


public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.topic.id = :topicId AND v.liked = true")
    int countLikesByTopicId(@Param("topicId") Long topicId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.topic.id = :topicId AND v.liked = false")
    int countDislikesByTopicId(@Param("topicId") Long topicId);

    Optional<Vote> findByUserEmailAndTopicId(String userEmail, Long topicId);

    long countByUserEmailAndVoteDate(String userEmail, LocalDate voteDate);
}

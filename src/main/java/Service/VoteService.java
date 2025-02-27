package Service;

import Repository.TopicRepository;
import Repository.VoteRepository;
import TableDao.Vote;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final TopicRepository topicRepository;

    public VoteService(VoteRepository voteRepository, TopicRepository topicRepository) {
        this.voteRepository = voteRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public String voteTopic(String userEmail, Long topicId, boolean liked) {
        LocalDate today = LocalDate.now();

        // Hitung jumlah votes user hari ini
        long userVotesToday = voteRepository.countByUserEmailAndVoteDate(userEmail, today);
        if (userVotesToday >= 5) {
            return "You have reached the maximum number of votes for today.";
        }

        // Cek apakah user sudah memberikan vote untuk topik ini
        Optional<Vote> existingVote = voteRepository.findByUserEmailAndTopicId(userEmail, topicId);

        if (existingVote.isPresent()) {
            Vote vote = existingVote.get();

            // Jika user mengklik lagi vote yang sama, hapus vote
            if (vote.isLiked() == liked) {
                voteRepository.delete(vote);
                return liked ? "Like removed." : "Dislike removed.";
            } else {
                // Jika user mengganti vote, perbarui status
                vote.setLiked(liked);
                voteRepository.save(vote);
                return liked ? "Switched to Like." : "Switched to Dislike.";
            }
        }

        // Jika belum pernah vote sebelumnya, buat vote baru
        Vote newVote = new Vote();
        newVote.setUserEmail(userEmail);
        newVote.setTopic(topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + topicId)));
        newVote.setLiked(liked);
        newVote.setVoteDate(today);

        voteRepository.save(newVote);
        return liked ? "Liked!" : "Disliked!";
    }

    public int getLikesCount(Long topicId) {
        return voteRepository.countLikesByTopicId(topicId);
    }

    public int getDislikesCount(Long topicId) {
        return voteRepository.countDislikesByTopicId(topicId);
    }
}
package Controller;

import Service.VoteService;
import TableDao.Vote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<?> voteTopic(@RequestBody Vote vote) {
        String responseMessage = voteService.voteTopic(vote.getUserEmail(), vote.getTopic().getId(), vote.isLiked());
        return ResponseEntity.ok().body(responseMessage);
    }

    @GetMapping("/likes/{topicId}")
    public ResponseEntity<?> getLikesCount(@PathVariable Long topicId) {
        int likeCount = voteService.getLikesCount(topicId);
        int dislikeCount = voteService.getDislikesCount(topicId);
        return ResponseEntity.ok(Map.of("topicId", topicId, "likes", likeCount, "dislikes", dislikeCount));
    }
}

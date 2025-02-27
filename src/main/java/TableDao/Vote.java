package TableDao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "votes")
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private boolean liked; // true = like, false = dislike
    private LocalDate voteDate;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
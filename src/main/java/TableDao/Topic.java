package TableDao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String author;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Vote> votes;
}

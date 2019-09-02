package space.forloop.circles.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import space.forloop.circles.domain.Circle;

/** @author Chris Turner (chris@forloop.space) */
@Repository
public interface CircleRepository extends ReactiveMongoRepository<Circle, String> {

  Flux<Circle> findAllByMembersIsIn(String user);

  Flux<Circle> findAllByOwnersIsIn(String user);
}

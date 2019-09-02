package space.forloop.circles.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import space.forloop.circles.domain.Circle;
import space.forloop.circles.exceptions.ForbiddenException;
import space.forloop.circles.repository.CircleRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static space.forloop.circles.service.TestUtil.TEST_USER_ONE;
import static space.forloop.circles.service.TestUtil.TEST_USER_TWO;

@RunWith(MockitoJUnitRunner.class)
public class CircleServiceImplTest {

  private Circle circleOne;

  @InjectMocks private CircleServiceImpl circleServiceMock;

  @Mock private CircleRepository circleRepositoryMock;

  @Mock private SecurityService securityServiceMock;

  @Before
  public void setUp() {
    this.circleOne =
        Circle.builder()
            .id("1")
            .name("test circle 1")
            .owners(TestUtil.getMapUser(TEST_USER_ONE))
            .members(TestUtil.getMapUser(TEST_USER_ONE))
            .created(1)
            .build();

    when(circleRepositoryMock.findById("1")).thenReturn(Mono.just(circleOne));

    when(circleRepositoryMock.findById("2"))
        .thenReturn(
            Mono.just(
                Circle.builder()
                    .id("2")
                    .name("test circle 2")
                    .owners(TestUtil.getMapUser(TEST_USER_TWO))
                    .members(TestUtil.getMapUser(TEST_USER_TWO))
                    .created(1)
                    .build()));
  }

  @Test
  public void whenCreate_create_ExpectCircle() {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_ONE));
    when(circleRepositoryMock.save(any())).thenReturn(Mono.just(circleOne));

    StepVerifier.create(circleServiceMock.create("test circle 1", null))
        .assertNext(a -> Assert.assertEquals("test circle 1", a.getName()))
        .expectComplete()
        .verify();
  }

  @Test
  public void whenFindByIdWhereOwner_ExpectCircle() {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_ONE));

    StepVerifier.create(circleServiceMock.findByIdWhereOwner("1"))
        .expectNext(circleOne)
        .expectComplete()
        .verify();
  }

  @Test
  public void whenFindByIdWhereNotOwner_ExpectForbidden() {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_TWO));

    StepVerifier.create(circleServiceMock.findByIdWhereOwner("1"))
        .expectErrorMatches(throwable -> throwable instanceof ForbiddenException)
        .verify();
  }

  @Test
  public void findAllByMembersIsIn_ExpectCircle() {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_ONE));
    when(circleRepositoryMock.findAllByMembersIsIn(TEST_USER_ONE)).thenReturn(Flux.just(circleOne));

    StepVerifier.create(circleServiceMock.findAllWhereMember())
        .expectNext(circleOne)
        .expectComplete()
        .verify();
  }

  @Test
  public void whenFindByIdWhereMember_ExpectCircle() {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_ONE));

    StepVerifier.create(circleServiceMock.findByIdWhereMember("1"))
        .expectNext(circleOne)
        .expectComplete()
        .verify();
  }

  @Test
  public void whenFindByIdWhereNotMember_ExpectCircle_ExpectForbidden() throws ForbiddenException {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_ONE));

    StepVerifier.create(circleServiceMock.findByIdWhereMember("2"))
        .expectErrorMatches(throwable -> throwable instanceof ForbiddenException)
        .verify();
  }

  @Test
  public void whenFindAllWhereOwner_ExpectCircle() {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_ONE));
    when(circleRepositoryMock.findAllByOwnersIsIn(TEST_USER_ONE)).thenReturn(Flux.just(circleOne));

    StepVerifier.create(circleServiceMock.findAllWhereOwner())
        .expectNext(circleOne)
        .expectComplete()
        .verify();
  }

  @Test
  public void whenFindAllWhereNotOwner_ExpectNullPointer() throws ForbiddenException {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_TWO));

    StepVerifier.create(circleServiceMock.findAllWhereOwner())
        .expectErrorMatches(throwable -> throwable instanceof NullPointerException)
        .verify();
  }

  @Test
  public void whenFindByIdWhereNotOwner_ExpectNullPointer() throws ForbiddenException {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_TWO));

    StepVerifier.create(circleServiceMock.findByIdWhereOwner("1"))
        .expectErrorMatches(throwable -> throwable instanceof ForbiddenException)
        .verify();
  }

  @Test
  public void whenAddMember_ExpectCircle() {
    final Circle expected = circleOne;
    expected.getMembers().add(TEST_USER_TWO);

    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_ONE));
    when(circleRepositoryMock.save(any())).thenReturn(Mono.just(expected));

    StepVerifier.create(circleServiceMock.addMember(TEST_USER_ONE, "1"))
        .expectNext(expected)
        .expectComplete()
        .verify();
  }

  @Test
  public void whenAddMemberWhereNotOwner_ExpectForbidden() {
    when(securityServiceMock.getUsername()).thenReturn(Mono.just(TEST_USER_TWO));

    StepVerifier.create(circleServiceMock.addMember(TEST_USER_ONE, "1"))
        .expectErrorMatches(throwable -> throwable instanceof ForbiddenException)
        .verify();
  }
}

package com.codeking.gamificationservice.repository

import com.codeking.gamificationservice.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import spock.lang.Specification
import spock.lang.Subject

@Subject(UserRepository)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserRepositorySpec extends Specification {

    @Autowired
    private UserRepository userRepository

    def "should return ten users with highest score"() {
        given: "20 users"
        def users = (1..20).collect { n ->
            return User.builder()
                    .score(n)
                    .userId(n.toString())
                    .build()
        }
        userRepository.saveAll(users)
        when: "ask for top 10"
        def topUsers = userRepository.findTop10UsersByOrderByScoreDesc()
        then:
        topUsers.size() == 10
        topUsers.stream().collect { user -> user.score }.toList() == (20..11).toList()
    }
}

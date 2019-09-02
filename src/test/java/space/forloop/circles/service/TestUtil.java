package space.forloop.circles.service;

import java.util.HashSet;
import java.util.Set;

class TestUtil {

  protected static String TEST_USER_ONE = "chris";

  protected static String TEST_USER_TWO = "steve";

  protected static Set<String> getMapUser(final String username) {
    final Set<String> userMap = new HashSet<>();
    userMap.add(username);

    return userMap;
  }
}

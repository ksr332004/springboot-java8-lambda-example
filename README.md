springboot-java8-lambda-example
===============================
> Java 8의 Lambda식을 적용하여 Spring Boot 개발하기

람다식(lambda expression)
------------------------
> 메소드를 하나의 식으로 표현한 것
   
~~~
# 표현방식
(매개변수) -> {실행코드}
~~~

* 클래스 작성과 객체 생성 없이 메서드 사용가능하다. 즉, 람다식으로 표현한 매서드는 익명함수(anonymous function) 이다.

[ClassicJavaTest.java](src/main/java/com/lambda/demo/test/ClassicJavaTest.java)
~~~java
interface A {
    int add(int a, int b);
}

public class ClassicJavaTest {
    public static void main(String args[]) {
        A a = new A(){
            public int add(int a, int b) {
                return a+b;
            }
        };
        System.out.println("add(1,2) = " + a.add(1, 2));
    }
}
~~~

[ModernJavaTest.java](src/main/java/com/lambda/demo/test/ModernJavaTest.java)
~~~java
interface B {
    int add(int a, int b);
}

public class ModernJavaTest {
    public static void main(String args[]) {
        B b = (x,y) -> x + y;
        System.out.println("add(1,2) = " + b.add(1, 2));
    }
}
~~~

* 함수형 인터페이스(functional interface)를 통해 메서드를 변수로 전달 할 수 있다.

[ListToStringFunction.java](src/main/java/com/lambda/demo/interfaces/ListToStringFunction.java)
~~~java
// 람다 인터페이스 선언
@FunctionalInterface
public interface ListToStringFunction {
    // 인터페이스의 추상 메서드가 두개 이상이 되면 오류
    String execute(List<String> list);
}
~~~

[ModernJavaController.java](src/main/java/com/lambda/demo/controller/ModernJavaController.java)
~~~java
@RestController
@RequestMapping(value = "/email")
public class ModernJavaController {
    private final ModernJavaUserService modernJavaUserService;

    public ModernJavaController(ModernJavaUserService modernJavaUserService) {
        this.modernJavaUserService = modernJavaUserService;
    }

    // ListToStringFunction 인터페이스를 통해 execute 메서드를 변수로 전달 받음
    private static String transformList(List<String> list, ListToStringFunction function) {
        return function.execute(list);
    }
    
    @GetMapping("/colon/{name}")
    public String getUserEmailWithColon(@PathVariable("name") String name) {
        List<String> emails = modernJavaUserService.findUserEmailByName(name);
        return transformList(emails, (l) -> String.join(":", l));
    }
}
~~~

* 기본 컬렉션 API가 개선된 Stream 인터페이스 비교

[ClassicJavaUserServiceImpl.java](src/main/java/com/lambda/demo/service/ClassicJavaUserServiceImpl.java)
~~~java
@Service
public class ClassicJavaUserServiceImpl implements ClassicJavaUserService {

    private final UserRepository userRepository;

    public ClassicJavaUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<String> findUserEmailByName(String name) {
        List<User> allUsers = userRepository.findAll();
        List<User> filteredUsers = filterByName(allUsers, name);
        sortById(filteredUsers);
        return mapEmails(filteredUsers);
    }

    // 사용자 이름 기준으로 필터링
    private List<User> filterByName(List<User> users, String name) {
        List<User> filtered = new ArrayList<>();
        for (User user : users) {
            if (name.equals(user.getName())) {
                filtered.add(user);
            }
        }
        return filtered;
    }

    // 사용자 아이디 기준으로 정렬
    private void sortById(List<User> users) {
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o1.getId(), o2.getId());
            }
        });
    }

    // 사용자 정보 중 이메일 정보만 가져오기
    private List<String> mapEmails(List<User> users) {
        List<String> emails = new ArrayList<>();
        for (User user : users) {
            emails.add(user.getEmail());
        }
        return emails;
    }

}
~~~

[ModernJavaUserServiceImpl.java](src/main/java/com/lambda/demo/service/ModernJavaUserServiceImpl.java)
~~~java
@Service
public class ModernJavaUserServiceImpl implements ModernJavaUserService {
    private final UserRepository userRepository;

    public ModernJavaUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<String> findUserEmailByName(String name) {
        List<User> users = userRepository.findAll();
        return users.stream()
                // 사용자 이름 기준으로 필터링
                .filter(user -> name.equals(user.getName()))
                // 사용자 아이디 기준으로 정렬
                .sorted(Comparator.comparing(User::getId))
                // 사용자 정보 중 이메일 정보만 가져오기
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

}
~~~
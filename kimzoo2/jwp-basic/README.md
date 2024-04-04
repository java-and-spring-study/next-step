#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
1. ContextLoaderListener의 contextInitialized를 통해 초기화를 시작한다.
2. ResourceDatabasePopulator 클래스로 외부에 정의된 script를 통해 데이터베이스를 초기화하고 데이터를 생성한다.
3. DatabasePopulatorUtils를 활용해 DB와 커넥션을 생성한다.
4. 프론트 컨트롤러인 DispatcherServlet으로 HTTP 요청이 전달된다.
5. DispatcherServlet는 최초에 요청을 처리할 컨트롤러를 담은 저장소인 RequestMapping을 생성 및 초기화한다. 

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
1. 로컬 서버를 통해 HTTP 요청이 서버로 전달되면 filter를 통해 필요한 작업이 처리된다
2. dispatcherServlet이 실행되어 요청에 맞는 컨트롤러를 RequestMapping 클래스에서 찾는다.
3. 요청을 컨트롤러에 전달하여 컨트롤러에게 위임하여 비즈니스 로직을 처리한다.
4. 컨트롤러는 DAO를 통해 데이터를 조회하거나 조작한다.
5. dispatcherServlet은 반환된 view로 포워딩한다.
6. HTTP 응답을 전달 받은 브라우저는 필요한 정적 데이터를 서버로 요청한다.
7. default servlet을 처리하는 필터는 필요한 정적 데이터를 찾아 브라우저에 응답한다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 인스턴스 변수 중, 결과를 저장하는 question과 answers가 공유자원이기 때문이다. showController는 서블릿 컨테이너가 인스턴스를 한 번 생성하기 때문에 모든 요청이 공유하는 자원이 된다.
* 그렇기 때문에 내부의 인스턴스 변수 역시 공유하는 자원이 되기 때문에 thread-safe하지 않다.

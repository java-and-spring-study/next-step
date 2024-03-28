#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 초기화 작업은 ServletContextListener를 구현한 ContextLoaderListener의 contextInitialized() method에서 시작된다. 
  * ServletContextListener는 웹 서버의 시작과 끝에 실행 되는 interface이다.
* conextInitialized() method에서 Database의 Script를 실행시킨다.
* 이후에는 DispatcherServlet의 init() method가 실행되고 초기화 된다.
* init() method에 존재하는 RequestMapping의 인스턴스를 생성시키고 RequestMapping을 초기화한다.


#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 호출 시 대기하고 있던 Socket에 TCP 3 way handshake 과정을 통해서 소켓과 연결되고 연결된 이후에 Http Message를 전송받는다.
* 전송 받은 Http Message를 가지고 HttpServletRequest, HttpServletResponse를 생성하고 HttpMessage의 Parsing과정을 거친다.
* HttpMessage의 요청 uri를 통해  uri에 매핑하는 Controller를 찾는다. 찾은 Controller를 실행하여 실행에 대한 결과를 ModelAndView로 받게된다.
  * Jsp일 경우에는 View만, Json일 경우에는 Client에게 필요한 데이터인 Model을 함께 전달 받는다.
* ModelAndView를 통해서 Client에게 HttpServletResponse를 변경 이후에 전달한다.
* TCP Socket이 4-way-handshake 과정을 통해서 연결을 해제한다.


#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* ShowController는 서버가 실행되고 초기화 시점에 싱글톤 객체로서 생성되고 ShowController를 모든 thread가 동일한 인스턴스인 ShowController에 접근이 가능하다.
* 이때 question field와 answers field가 공유 자원으로서 모든 thread에게 사용되기 때문에 문제가 발생한다.

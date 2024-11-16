서버 부분/클라이언트 부분
1. 처음에 서버와 클라이언트는 number.dat 파일에서부터 IP주소와 Port number를 읽는다.
2. 읽지 못한다면 try-catch 문을 이용해서 에러를 출력하게 만들었다.
3. 이후 서버에서는20개의 Thread를 만들고IP 주소와 Port number를 읽었다면 서버 소켓을 생성 후에 .accept()를 이용해서 Client의 연결을 기다린다.
4. 서버가 기다리고 있으면 클라이언트는 Socket을 생성해서 서버에 연결한다.
5. 연결이 되면 퀴즈가 진행이 된다. 

퀴즈 진행

5. 서버와 클라이언트가 연결되면 서버는 이때부터 퀴즈 배열을 이용해서 클라이언트에게 퀴즈를 제공한다. 클라이언트는 퀴즈 창과, 메시지 창을 둘 다 띄운다. 메시지는 end를 누를 경우 중간 종료 된다는 것을 알려준다.
6. 클라이언트는 떠 있는 퀴즈를 보고 답을 작성한다. 이때, GUI를 이용했기 때문에 작성되는 곳은 TextField 부분이다.
7. 답을 작성 후에 클라이언트 쪽에서 submit이라는 버튼을 클릭하게 되면 클라이언트에서 서버 쪽으로 작성된 답을 서버로 전달하고, 서버에서 반응하게 된다.
8. 서버는 받은 답을 퀴즈 정답 배열과 비교 후에 이것이 정답인지 오답인지 판단한다. 정답일 경우에는 정답 개수를 세고, 판단 후에는 res라는 변수를 이용하여 클라이언트에게 채점 결과를 보내준다.
9. 결과를 받은 클라이언트는 그것을 창으로 띄워서 답이 맞는지 틀린지 알려주고. 틀리다면 답은 무엇인지도 알려준다.
10. 이런 퀴즈를 쭉 진행하다가 퀴즈가 끝이 나면 클라이언트에서 퀴즈가 끝이 났음을 알린다.
11. 서버는 퀴즈를 진행하며 세었던 정답 개수를 클라이언트에게 보내주고, 클라이언트는 정답 개수를 창으로 띄워 보여준다.
12. 이후 소켓 연결을 닫고, frame 또한 닫아서 프로그램을 종료시킨다.

중간 종료
-클라이언트는 퀴즈 진행 도중에 end가 눌리면 socket, frame을 닫아주고. 서버는 end가 눌렸을 경우, null값을 받기 때문에 이 부분을 if문으로 처리하여 end가 눌렸을 경우에 클라이언트가 종료 신호를 보냈다는 것을 터미널로 보내도록했다.

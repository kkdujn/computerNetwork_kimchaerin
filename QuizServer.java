import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class QuizServer {
    static BufferedReader in = null;
    static BufferedWriter out = null;
    static ServerSocket listener = null;
    static Socket socket = null;

    public static void main(String[] args) {
        // Read the txt file to read the IP address and read the Port Number.

        try (Scanner scanner = new Scanner(new File("C:\\Univ\\Quiz_program\\src\\number.dat"))) {

            // Read IP Addresses from the First Line
            String ipAddress = scanner.nextLine();

            // Read Port number from the Second Line
            int portNumber = Integer.parseInt(scanner.nextLine());

            listener = new ServerSocket(portNumber); // Create a server socket
            System.out.println("Waiting for connection....");

            // Multi-thread implementation part
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                Socket sock = listener.accept();
                pool.execute(new Quizs(sock));
            }
        } catch (IOException e) {
            System.out.println("Error running server");
            e.printStackTrace();
        }
    }

    private static class Quizs implements Runnable {
        private Socket socket;

        Quizs(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                int answerCount = 0;
                int correctCount = 0;

                String[] quizSolutionList = { "10", "23", "43" };
                String[] quizList = { "7 + 3 = ", "20 + 3 = ", "40 + 3 = " };

                while (answerCount < quizSolutionList.length) { // No continuous turning, but as long as the problem
                                                                // length (3)
                    out.write(quizList[answerCount] + "\n");
                    out.flush();

                    String inputMessage = in.readLine();

                    if (inputMessage == null || inputMessage.equalsIgnoreCase("end")) {
                        System.out.println("receive a response from the client that program end.");
                        break;
                    }

                    System.out.println(inputMessage); // Output received messages to the termina

                    String res; // Save scoring results to res

                    if (inputMessage.equals(quizSolutionList[answerCount])) {
                        res = "Correct!";
                        correctCount++;
                    } else {
                        res = "Not Correct. Correct answer is " + quizSolutionList[answerCount];
                    }
                    out.write(res + "\n"); // Send results to clients
                    out.flush();

                    answerCount++;

                }
                if (answerCount >= 3) {
                    out.write(correctCount);
                    out.flush();
                }

            } catch (IOException e) {
                System.out.println("Error in communication");
                e.printStackTrace();
            } finally {
                try {
                    socket.close(); // Close Socket
                } catch (IOException e) {
                    System.out.println("Error terminating socket.");
                }

            }
        }

    }
}

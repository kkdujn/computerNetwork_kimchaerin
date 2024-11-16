import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizClient {
    static BufferedReader in = null;
    static BufferedWriter out = null;
    static Socket socket = null;
    static int count = 0;
    static String question;

    public static void main(String[] args) throws Exception {
        // --------------------14
        JFrame frame = new JFrame("Quiz"); // title name
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // width, height specification

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1)); // Grid: represented by rows, columns

        JLabel quizLabel = new JLabel("Quiz"); // Label : Quiz
        JTextField answerField = new JTextField();
        JButton submitButton = new JButton("Submit"); // create button

        panel.add(quizLabel);
        panel.add(answerField);
        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);

        try (Scanner Portscanner = new Scanner(new File("C:\\Univ\\Quiz_program\\src\\number.dat"))) {
            // Read IP Addresses from the First Line
            String ipAddress = Portscanner.nextLine();

            // Read Port number from the Second Line
            int portNumber = Integer.parseInt(Portscanner.nextLine());

            socket = new Socket(ipAddress, portNumber);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            JOptionPane.showMessageDialog(frame, "If you enter 'end', the program is closed.");

            // Enter end to indicate that the program will be terminated.
            question = in.readLine(); // Here, you receive an array of quizzes sent from the server (in order of sending index 0,1,2)
            // -> In this case, only the first (index 0) quiz to be shown is called first before the button is pressed.
            quizLabel.setText(question); // Change only text to a label location that you have already added

            submitButton.addActionListener(new ActionListener() { // Define the method that will run when you click submit
                public void actionPerformed(ActionEvent e) { // Run at click
                    try {

                        if (count >= 3) {
                            return;
                        }
                        String outputMsg = answerField.getText(); //Read what you write in the field and hand it over to the server.

                        if (outputMsg.equalsIgnoreCase("end")) {
                            JOptionPane.showMessageDialog(frame, "Program closed.");
                            socket.close();
                            frame.dispose();
                            return;
                        }

                        out.write(outputMsg + "\n"); // Transfers content from submit to server
                        // -> Error in out! -> actionListener prevents outside declared out from coming in -> declared outside at all.
                        out.flush();

                        String inputmsg = in.readLine();
                        
                        JOptionPane.showMessageDialog(frame, "result of your answer :" + inputmsg);
                        count++; // It should be here so that the last score can be printed from the bottom
                        if (count < 3) {
                            question = in.readLine();
                            quizLabel.setText(question);
                            answerField.setText(""); // -> If you get another question after submit, empty the text window.
                        }

                        else {
                            int correctCount = in.read();
                            JOptionPane.showMessageDialog(frame, "Quiz End.");
                            JOptionPane.showMessageDialog(frame, "Your quiz score : " + correctCount + "/" + count);
                            socket.close();
                            frame.dispose();
                        }

                    } catch (Exception ex) { //If you write e, it's wrong! -> Modify it to ex
                        ex.printStackTrace(); // Error Output
                    }
                }
            });

        } catch(FileNotFoundException e){
            System.out.println("can't find number.dat File.");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}

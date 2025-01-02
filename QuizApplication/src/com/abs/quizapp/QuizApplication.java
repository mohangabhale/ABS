package com.abs.quizapp;

import java.io.*;
import java.util.*;

class Question implements Serializable {
    private String questionText;
    private List<String> options;
    private int correctOption;

    public Question(String questionText, List<String> options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question: ").append(questionText).append("\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append(i + 1).append(". ").append(options.get(i)).append("\n");
        }
        return sb.toString();
    }
}

public class QuizApplication {
    private static final String FILE_NAME = "questions.ser";
    private List<Question> questions;
    private Scanner scanner;

    public QuizApplication() {
        questions = loadQuestions();
        scanner = new Scanner(System.in);
    }

    public void addQuestion() {
        System.out.print("Enter the question: ");
        String questionText = scanner.nextLine();

        List<String> options = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            System.out.print("Enter option " + (i + 1) + ": ");
            options.add(scanner.nextLine());
        }

        int correctOption;
        while (true) {
            System.out.print("Enter the number of the correct option (1-4): ");
            correctOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (correctOption >= 1 && correctOption <= 4) break;
            System.out.println("Invalid option. Please enter a number between 1 and 4.");
        }

        questions.add(new Question(questionText, options, correctOption));
        System.out.println("Question added successfully!");
    }

    public void editQuestion() {
        displayQuestions();
        System.out.print("Enter the question number to edit: ");
        int questionNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (questionNumber < 1 || questionNumber > questions.size()) {
            System.out.println("Invalid question number.");
            return;
        }

        Question question = questions.get(questionNumber - 1);

        System.out.print("Enter the new question text: ");
        question.setQuestionText(scanner.nextLine());

        List<String> newOptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            System.out.print("Enter new option " + (i + 1) + ": ");
            newOptions.add(scanner.nextLine());
        }
        question.setOptions(newOptions);

        int correctOption;
        while (true) {
            System.out.print("Enter the new correct option (1-4): ");
            correctOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (correctOption >= 1 && correctOption <= 4) break;
            System.out.println("Invalid option. Please enter a number between 1 and 4.");
        }
        question.setCorrectOption(correctOption);

        System.out.println("Question updated successfully!");
    }

    public void deleteQuestion() {
        displayQuestions();
        System.out.print("Enter the question number to delete: ");
        int questionNumber = scanner.nextInt();

        if (questionNumber < 1 || questionNumber > questions.size()) {
            System.out.println("Invalid question number.");
            return;
        }

        questions.remove(questionNumber - 1);
        System.out.println("Question deleted successfully!");
    }

    public void displayQuestions() {
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }

        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question " + (i + 1) + ":");
            System.out.println(questions.get(i));
        }
    }

    public void saveQuestions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(questions);
            System.out.println("Questions saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving questions: " + e.getMessage());
        }
    }

    public void startQuiz() {
        if (questions.isEmpty()) {
            System.out.println("No questions available to start the quiz.");
            return;
        }

        System.out.println("Welcome to the Quiz!");
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("Question " + (i + 1) + ":");
            System.out.println(question);

            int userAnswer;
            while (true) {
                System.out.print("Choose the correct option (1-4): ");
                userAnswer = scanner.nextInt();
                if (userAnswer >= 1 && userAnswer <= 4) break;
                System.out.println("Invalid option. Please enter a number between 1 and 4.");
            }

            if (userAnswer == question.getCorrectOption()) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was option " + question.getCorrectOption() + ".");
            }
        }

        System.out.println("Quiz finished! Your score: " + score + "/" + questions.size());
    }

    @SuppressWarnings("unchecked")
    private List<Question> loadQuestions() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Question>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        QuizApplication app = new QuizApplication();

        while (true) {
            System.out.println("\nQuiz Application Menu:");
            System.out.println("1. Add Question");
            System.out.println("2. Edit Question");
            System.out.println("3. Delete Question");
            System.out.println("4. Display Questions");
            System.out.println("5. Save and Exit");
            System.out.println("6. Start Quiz");

            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = app.scanner.nextInt();
                app.scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                app.scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    app.addQuestion();
                    break;
                case 2:
                    app.editQuestion();
                    break;
                case 3:
                    app.deleteQuestion();
                    break;
                case 4:
                    app.displayQuestions();
                    break;
                case 5:
                    app.saveQuestions();
                    System.exit(0);
                    break;
                case 6:
                    app.startQuiz();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}


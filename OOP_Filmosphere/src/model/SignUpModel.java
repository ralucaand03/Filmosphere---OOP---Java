package model;

import repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;

public class SignUpModel {
    private String username;
    private String password;
    private String email;
    private String phoneNumber; // Added field
    private String name; // Added field

    ///////////////////

    private List<SignUpObserver> observers = new ArrayList<>();

    public void addObserver(SignUpObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (SignUpObserver observer : observers) {
            observer.update(message);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmailValid() {
        // Implement email validation logic
        // For simplicity, consider an email as valid if it is not empty
        return email != null && !email.trim().isEmpty();
    }

    public boolean isPasswordValid() {
        // Implement password validation logic
        // For simplicity, consider a password as valid if it is not empty
        return password != null && !password.trim().isEmpty();
    }

    public String signUp() {
        // Validate input
        if (!isEmailValid() || !isPasswordValid()) {
            return "Invalid input. Please check your information.";
        }

        // Call repository to insert user
        UsersRepository usersRepository = new UsersRepository();
        String result = usersRepository.insertUser(username, password, email, phoneNumber, name);

        // Notify observers about the result
        notifyObservers(result);

        return result;
    }

    // Additional business logic...

    public interface SignUpObserver {
        void update(String message);
    }
}

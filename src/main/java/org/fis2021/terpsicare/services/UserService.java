package org.fis2021.terpsicare.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.terpsicare.exceptions.EmptyTextfieldsException;
import org.fis2021.terpsicare.exceptions.UsernameAlreadyExistsException;
import org.fis2021.terpsicare.exceptions.WrongPasswordConfirmationException;
import org.fis2021.terpsicare.model.User;
import org.fis2021.terpsicare.model.Doctor;
import org.fis2021.terpsicare.model.Admin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.fis2021.terpsicare.services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder().filePath(getPathToFile(".terpsicare-users.db").toFile()).openOrCreate("test", "test");
        userRepository = database.getRepository(User.class);
        int ok = 1;
        for (User user : userRepository.find()) {
            if (Objects.equals("admin", user.getUsername()))
                ok = 0;
        }
        if (ok == 1) {
            Admin admin = Admin.getInstance();
            userRepository.insert(admin);
        }
    }

    public static void addDoctor(String username, String password, String confirmedPassword, String name, String medicalSpecialty, String phoneNumber) throws UsernameAlreadyExistsException, WrongPasswordConfirmationException, EmptyTextfieldsException {
        checkUserDoesNotAlreadyExist(username);
        checkPasswordSameAsConfirmedPassword(password, confirmedPassword);
        checkEmptyTextFields(username, password, confirmedPassword, name, medicalSpecialty, phoneNumber);
        userRepository.insert(new Doctor(username, encodePassword(username, password), name, medicalSpecialty, phoneNumber));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static void checkPasswordSameAsConfirmedPassword(String password, String confirmedPassword) throws WrongPasswordConfirmationException {
        if(!Objects.equals(password, confirmedPassword)) {
            throw new WrongPasswordConfirmationException();
        }
    }

    private static void checkEmptyTextFields(String username, String password, String confirmedPassword, String name, String medicalSpecialty, String phoneNumber) throws EmptyTextfieldsException {
        if (Objects.equals(username, ""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(password, ""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(confirmedPassword, ""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(name, ""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(medicalSpecialty, null))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(phoneNumber, ""))
            throw new EmptyTextfieldsException();
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
}
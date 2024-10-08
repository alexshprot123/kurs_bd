package org.example.Forms.admin.doctor.modal;

import org.example.BD;
import org.example.Consts;
import org.example.entity.Doctor;
import org.example.entity.MedUser;
import org.example.entity.Profession;
import org.hibernate.criterion.NullExpression;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class AddDoctorForm extends JDialog {
    private JPanel panel;
    private JButton backButton;
    private JButton okButton;
    private JTextField fioTextField;
    private JComboBox genderComboBox;
    private JTextField sertifTextField;
    private JTextField phoneTextField;
    private JComboBox profComboBox;
    private JTextField birchTextField;
    private JLabel errorMessageLabel;
    private JTextField loginTextField;
    private JTextField passwordTextField;

    public AddDoctorForm(JFrame parent){
        super(parent, "", true);
        setContentPane(panel);
        setSize(400, 400);

        errorMessageLabel.setVisible(false);

        List<Profession> profs = BD.getAllProfessions();
        List<String> profsName = profs.stream().map(p -> p.name).collect(Collectors.toList());
        for(String prof : profsName)
            profComboBox.addItem(prof);

        genderComboBox.addItem(Consts.GENDER_MALE);
        genderComboBox.addItem(Consts.GENDER_FEMALE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(addDoctor())
                    dispose();
            }
        });
    }

    public boolean addDoctor(){
        try {
            checkNull();
            Doctor doctor = getDoctor();
            MedUser user = getUser();
            BD.addDoctor(doctor);
            user.targetId = doctor.id;
            BD.addUser(user);
            return true;
        }catch (DateTimeParseException dateTimeParseException){
            System.out.println(dateTimeParseException.getMessage());
            showError("Неверно указана дата. Дата должна быть вида dd.MM.yyyy");
        }catch (NullPointerException nullPointerException){
            showError("Все поля должны быть заполнены");
        }
        return false;
    }
    public void showError(String msg){
        errorMessageLabel.setText(msg);
        errorMessageLabel.setVisible(true);
    }

    public static void showDialog(JFrame parent) {
        AddDoctorForm dialog = new AddDoctorForm(parent);
        dialog.setVisible(true);
    }

    private MedUser getUser(){
        MedUser user = new MedUser();
        user.login = loginTextField.getText();
        user.password = passwordTextField.getText();
        user.role = Consts.ROLE_DOCTOR;
        return user;
    }
    private Doctor getDoctor() {
        Doctor doctor = new Doctor();
        doctor.name = fioTextField.getText();
        doctor.gender = genderComboBox.getSelectedItem().toString();
        doctor.birthdate = LocalDate.parse(birchTextField.getText(), Consts.DATE_FORMAT);
        doctor.certificate = sertifTextField.getText();
        doctor.profession = BD.getProfessionByName(profComboBox.getSelectedItem().toString());
        doctor.startWork = LocalDate.now();
        doctor.phone = phoneTextField.getText();

        return doctor;
    }
    private void checkNull() throws NullPointerException{
        if(fioTextField.getText().isEmpty() || birchTextField.getText().isEmpty() || sertifTextField.getText().isEmpty() || phoneTextField.getText().isEmpty() ||
                loginTextField.getText().isEmpty() || passwordTextField.getText().isEmpty())
            throw new NullPointerException("Все поля должны быть заполнены");
    }
}

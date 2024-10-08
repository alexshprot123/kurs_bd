package org.example.Forms.admin.patient.modal;

import org.example.BD;
import org.example.Consts;
import org.example.entity.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class AddPatientForm extends JDialog{

    private JTextField fioTextField;
    private JComboBox genderComboBox;
    private JTextField insureTextField;
    private JTextField phoneTextField;
    private JComboBox disComboBox;
    private JTextField birchTextField;
    private JButton backButton;
    private JButton okButton;
    private JLabel errorMessageLabel;
    private JPanel panel;
    private JTextField loginTextField;
    private JTextField passwordTextField;

    public AddPatientForm(JFrame parent){
        super(parent, "", true);
        setContentPane(panel);
        setSize(500, 500);

        errorMessageLabel.setVisible(false);

        List<Disease> dis = BD.getAllDisease();
        List<String> disName = dis.stream().map(d -> d.name).collect(Collectors.toList());
        for(String prof : disName)
            disComboBox.addItem(prof);

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
            Patient patient = getPatient();
            MedUser user = getUser();
            BD.addPatient(patient);
            user.targetId = patient.id;
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
        AddPatientForm dialog = new AddPatientForm(parent);
        dialog.setVisible(true);
    }

    private MedUser getUser(){
        MedUser user = new MedUser();
        user.login = loginTextField.getText();
        user.password = passwordTextField.getText();
        user.role = Consts.ROLE_PATIENT;
        return user;
    }
    private Patient getPatient() {
        Patient patient = new Patient();
        patient.name = fioTextField.getText();
        patient.gender = genderComboBox.getSelectedItem().toString();
        patient.birthdate = LocalDate.parse(birchTextField.getText(), Consts.DATE_FORMAT);
        patient.snils = insureTextField.getText();
        patient.disease = BD.getDiseaseByName(disComboBox.getSelectedItem().toString());
        patient.phone = phoneTextField.getText();
        patient.doctor = getDoctor(patient);

        return patient;
    }
    private Doctor getDoctor(Patient patient){
        String doctorType = "Терапевт";
        if(patient.disease.type != null && patient.disease.type.equals("Кардиология"))
            doctorType = "Кардиолог";
        else if (patient.disease.type != null && patient.disease.type.equals("Онкология"))
            doctorType = "Онколог";

        Profession profession = BD.getProfessionByName(doctorType);
        List<Doctor> doctors = BD.getDoctorsByProfession(profession.id);
        Doctor freeDoctor = null;
        long min = 999;
        for(Doctor d : doctors){
            long count = BD.getPatientCountByDoctor(d);
            if(count < min) {
                freeDoctor = d;
                min = count;
            }
        }
        return freeDoctor;
    }
    private void checkNull() throws NullPointerException{
        if(fioTextField.getText().isEmpty() || birchTextField.getText().isEmpty() || insureTextField.getText().isEmpty() || phoneTextField.getText().isEmpty() ||
                loginTextField.getText().isEmpty() || passwordTextField.getText().isEmpty())
            throw new NullPointerException("Все поля должны быть заполнены");
    }
}

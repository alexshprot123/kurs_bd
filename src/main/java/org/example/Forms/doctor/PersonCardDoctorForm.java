package org.example.Forms.doctor;

import org.example.BD;
import org.example.Consts;
import org.example.MainForm;
import org.example.entity.Doctor;
import org.example.entity.MedUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PersonCardDoctorForm extends JPanel{
    private JTextField fioTextField;
    private JTextField sertifTextField;
    private JTextField phoneTextField;
    private JTextField birchTextField;
    private JTextField genderTextField;
    private JTextField profTextField;
    private JPanel panel;
    JFrame frame;
    Doctor doctor;

    public PersonCardDoctorForm(JFrame frame){
        this.frame = frame;
        add(panel);
        setSize(-1, -1);
        setVisible(true);
    }
    public void initFields(){
        fioTextField.setText(doctor.name);
        genderTextField.setText(doctor.gender);
        birchTextField.setText(doctor.birthdate.format(Consts.DATE_FORMAT));
        phoneTextField.setText(doctor.phone);
        sertifTextField.setText(doctor.certificate);
        profTextField.setText(doctor.profession.name);
    }
    public void updateForm(){
        MedUser user = MainForm.getCurUser();
        if(user == null)
            return;
        doctor = BD.getDoctorById(user.targetId);
        initFields();
    }
}

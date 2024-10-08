package org.example.Forms.patient;

import org.example.BD;
import org.example.Consts;
import org.example.MainForm;
import org.example.entity.MedUser;
import org.example.entity.Patient;

import javax.swing.*;

public class PersonCardPatientForm extends JPanel{
    private JTextField fioTextField;
    private JTextField insureTextField;
    private JTextField phoneTextField;
    private JTextField birchTextField;
    private JTextField genderTextField;
    private JTextField disTextField;
    private JPanel panel;
    private JPanel panel2;
    JFrame frame;
    Patient patient;

    public PersonCardPatientForm(JFrame frame){
        this.frame = frame;
        add(panel);
        initColors();
        setSize(-1, -1);
        setVisible(true);
    }
    private void initColors(){
//        panel.setBackground(Consts.MINI_PANEL_COLOR);
//        panel2.setBackground(Consts.MINI_PANEL_COLOR);
//        panel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void initFields(){
        fioTextField.setText(patient.name);
        genderTextField.setText(patient.gender);
        birchTextField.setText(patient.birthdate.format(Consts.DATE_FORMAT));
        phoneTextField.setText(patient.phone);
        disTextField.setText(patient.disease.name);
        insureTextField.setText(patient.snils);
    }
    public void updateForm(){
        MedUser user = MainForm.getCurUser();
        if(user == null)
            return;
        patient = BD.getPatientById(user.targetId);
        initFields();
    }
}

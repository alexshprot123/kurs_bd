package org.example.Forms.admin.patient.modal;

import org.example.Consts;
import org.example.entity.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPatientForm  extends JDialog {

    private JPanel panel;
    private JTextField fioTextField;
    private JTextField insureTextField;
    private JTextField phoneTextField;
    private JTextField birchTextField;
    private JTextField genderTextField;
    private JTextField disTextField;
    private JButton backButton;
    private JTextField doctorTextField;
    Patient patient;

    public InfoPatientForm(JFrame parent, Patient patient){
        super(parent, "", true);
        this.patient = patient;
        initFields();
        setContentPane(panel);
        setSize(500, 500);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void initFields(){
        fioTextField.setText(patient.name);
        genderTextField.setText(patient.gender);
        birchTextField.setText(patient.birthdate.format(Consts.DATE_FORMAT));
        phoneTextField.setText(patient.phone);
        disTextField.setText(patient.disease.name);
        insureTextField.setText(patient.snils);
        doctorTextField.setText(patient.doctor.name);
    }

    public static void showDialog(JFrame parent, Patient patient) {
        InfoPatientForm dialog = new InfoPatientForm(parent, patient);
        dialog.setVisible(true);
    }
}

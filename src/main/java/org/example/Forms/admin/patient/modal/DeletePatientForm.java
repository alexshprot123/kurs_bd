package org.example.Forms.admin.patient.modal;

import org.example.BD;
import org.example.Consts;
import org.example.entity.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePatientForm  extends JDialog {

    private JTextField fioTextField;
    private JTextField insureTextField;
    private JTextField phoneTextField;
    private JTextField birchTextField;
    private JTextField genderTextField;
    private JTextField disTextField;
    private JLabel errorMessageLabel;
    private JButton backButton;
    private JButton okButton;
    private JPanel panel;
    Patient patient;

    public DeletePatientForm(JFrame parent, Patient patient){
        super(parent, "", true);
        this.patient = patient;
        initFields();
        setContentPane(panel);
        setSize(500, 500);

        errorMessageLabel.setVisible(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatient();
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
    }

    public void deletePatient(){
        BD.deleteAppointedByPatient(patient);
        BD.deletePatientUser(patient);
        BD.deletePatient(patient);
    }
    public void showError(String msg){
        errorMessageLabel.setText(msg);
        errorMessageLabel.setVisible(true);
    }

    public static void showDialog(JFrame parent, Patient patient) {
        DeletePatientForm dialog = new DeletePatientForm(parent, patient);
        dialog.setVisible(true);
    }
}

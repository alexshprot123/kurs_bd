package org.example.Forms.admin.doctor.modal;

import org.example.Consts;
import org.example.entity.Doctor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoDoctorForm extends JDialog {
    private JTextField fioTextField;
    private JTextField sertifTextField;
    private JTextField phoneTextField;
    private JTextField birchTextField;
    private JTextField genderTextField;
    private JTextField profTextField;
    private JButton backButton;
    private JPanel panel;
    Doctor doctor;

    public InfoDoctorForm(JFrame parent, Doctor doctor){
        super(parent, "", true);
        this.doctor = doctor;
        setContentPane(panel);
        initFields();
        setSize(400, 400);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void initFields(){
        fioTextField.setText(doctor.name);
        genderTextField.setText(doctor.gender);
        birchTextField.setText(doctor.birthdate.format(Consts.DATE_FORMAT));
        phoneTextField.setText(doctor.phone);
        sertifTextField.setText(doctor.certificate);
        profTextField.setText(doctor.profession.name);
    }

    public static void showDialog(JFrame parent, Doctor doctor) {
        InfoDoctorForm dialog = new InfoDoctorForm(parent, doctor);
        dialog.setVisible(true);
    }
}

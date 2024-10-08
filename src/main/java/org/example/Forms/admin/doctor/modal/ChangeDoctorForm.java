package org.example.Forms.admin.doctor.modal;

import org.example.BD;
import org.example.Consts;
import org.example.entity.Doctor;
import org.example.entity.Profession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeDoctorForm extends JDialog {
    private JTextField fioTextField;
    private JTextField sertifTextField;
    private JTextField phoneTextField;
    private JComboBox profComboBox;
    private JTextField birchTextField;
    private JButton backButton;
    private JButton okButton;
    private JLabel errorMessageLabel;
    private JPanel panel;
    private JTextField genderTextField;
    Doctor doctor;
    List<Profession> profs = new ArrayList<>();

    public ChangeDoctorForm(JFrame parent, Doctor doctor){
        super(parent, "", true);
        this.doctor = doctor;
        setContentPane(panel);
        initFields();
        setSize(400, 400);

        profs = BD.getAllProfessions();
        List<String> profsName = profs.stream().map(p -> p.name).collect(Collectors.toList());
        for(String prof : profsName)
            profComboBox.addItem(prof);

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
                if(changeDoctor())
                    dispose();
            }
        });
    }

    public boolean changeDoctor(){
        try {
            Profession newProf = profs.get(profComboBox.getSelectedIndex());
            BD.updateDoctor(doctor, newProf);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public void initFields(){
        fioTextField.setText(doctor.name);
        genderTextField.setText(doctor.gender);
        birchTextField.setText(doctor.birthdate.format(Consts.DATE_FORMAT));
        phoneTextField.setText(doctor.phone);
        sertifTextField.setText(doctor.certificate);
        //profTextField.setText(doctor.profession.name);
    }

    public void showError(String msg){
        errorMessageLabel.setText(msg);
        errorMessageLabel.setVisible(true);
    }

    public static void showDialog(JFrame parent, Doctor doctor) {
        ChangeDoctorForm dialog = new ChangeDoctorForm(parent, doctor);
        dialog.setVisible(true);
    }
}

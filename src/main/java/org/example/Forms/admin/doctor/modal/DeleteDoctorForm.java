package org.example.Forms.admin.doctor.modal;

import org.example.BD;
import org.example.Consts;
import org.example.entity.Doctor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteDoctorForm extends JDialog {
    private JTextField fioTextField;
    private JTextField sertifTextField;
    private JTextField phoneTextField;
    private JTextField birchTextField;
    private JButton backButton;
    private JButton okButton;
    private JLabel errorMessageLabel;
    private JTextField genderTextField;
    private JTextField profTextField;
    private JPanel panel;
    private JComboBox comboBox1;
    Doctor doctor;
    List<Doctor> ds;

    public DeleteDoctorForm(JFrame parent, Doctor doctor){
        super(parent, "", true);
        this.doctor = doctor;
        setContentPane(panel);
        initFields();
        setSize(400, 400);

        errorMessageLabel.setVisible(false);
        ds = BD.getDoctorsByProfession(doctor.profession.id);
        ds.removeIf(d -> d.id == doctor.id);
        List<String> profsName = ds.stream().map(p -> p.name).collect(Collectors.toList());
        profsName.add(0, "<<Не заменять>>");
        for(String prof : profsName)
            comboBox1.addItem(prof);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(deleteDoctor())
                    dispose();
            }
        });
    }

    public boolean deleteDoctor(){
        if(comboBox1.getSelectedIndex() == 0){
            if(BD.getPatientsByDoctor(doctor.id).size() > 0){
                showError("Нельзя уволить без замены.");
                return false;
            }
            BD.deleteAppointedByDoctor(doctor);
        }else {
            Doctor newDoctor = ds.get(comboBox1.getSelectedIndex()-1);
            BD.updatePatients(doctor, newDoctor);
            BD.updateAppointed(doctor, newDoctor);
        }
        BD.deleteDoctorUser(doctor);
        BD.deleteDoctor(doctor);
        return true;
    }
    public void initFields(){
        fioTextField.setText(doctor.name);
        genderTextField.setText(doctor.gender);
        birchTextField.setText(doctor.birthdate.format(Consts.DATE_FORMAT));
        phoneTextField.setText(doctor.phone);
        sertifTextField.setText(doctor.certificate);
        profTextField.setText(doctor.profession.name);
    }
    public void showError(String msg){
        errorMessageLabel.setText(msg);
        errorMessageLabel.setVisible(true);
    }

    public static void showDialog(JFrame parent, Doctor doctor) {
        DeleteDoctorForm dialog = new DeleteDoctorForm(parent, doctor);
        dialog.setVisible(true);
    }
}

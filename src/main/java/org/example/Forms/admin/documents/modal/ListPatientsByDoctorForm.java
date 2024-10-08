package org.example.Forms.admin.documents.modal;

import org.example.BD;
import org.example.DocumentWriter;
import org.example.entity.Disease;
import org.example.entity.Doctor;
import org.example.entity.Patient;
import org.example.entity.Profession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListPatientsByDoctorForm extends JDialog{
    private JComboBox profComboBox;
    private JTextField nameTextField;
    private JLabel errorMessageLabel;
    private JButton backButton;
    private JButton okButton;
    private JComboBox doctorTextField;
    private JPanel panel;

    List<Profession> professions = new ArrayList<>();
    List<Doctor> doctors = new ArrayList<>();

    public ListPatientsByDoctorForm(JFrame parent){
        super(parent, "", true);
        setContentPane(panel);
        setSize(400, 300);
        errorMessageLabel.setVisible(false);
        professions = BD.getAllProfessions();
        List<String> profsName = professions.stream().map(p -> p.name).collect(Collectors.toList());
        for(String prof : profsName)
            profComboBox.addItem(prof);
        updateList();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(createDocument())
                    dispose();
            }
        });
        profComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList();
            }
        });
    }

    public void updateList(){
        Profession profession = professions.get(profComboBox.getSelectedIndex());
        doctors = BD.getDoctorsByProfession(profession.id);
        doctorTextField.removeAllItems();
        List<String> doctorNames = doctors.stream().map(p -> p.name).collect(Collectors.toList());
        for(String dn : doctorNames)
            doctorTextField.addItem(dn);
    }

    public void showError(String msg){
        errorMessageLabel.setText(msg);
        errorMessageLabel.setVisible(true);
    }

    public boolean createDocument(){
        try {
            if(nameTextField.getText().isEmpty()){
                showError("Имя файла не указано");
                return false;
            }
            List<Patient> patients = BD.getPatientsByDoctor(doctors.get(doctorTextField.getSelectedIndex()).id);

            DocumentWriter.patientsDocument(patients, nameTextField.getText());
            return true;
        }catch (DateTimeParseException dateTimeParseException){
            System.out.println(dateTimeParseException.getMessage());
        }catch (NullPointerException nullPointerException){
        }
        return false;
    }

    public static void showDialog(JFrame parent) {
        ListPatientsByDoctorForm dialog = new ListPatientsByDoctorForm(parent);
        dialog.setVisible(true);
    }
}

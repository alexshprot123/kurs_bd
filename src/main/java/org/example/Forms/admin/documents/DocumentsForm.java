package org.example.Forms.admin.documents;

import org.example.BD;
import org.example.Forms.admin.doctor.modal.AddDoctorForm;
import org.example.Forms.admin.doctor.modal.ChangeDoctorForm;
import org.example.Forms.admin.doctor.modal.DeleteDoctorForm;
import org.example.Forms.admin.doctor.modal.InfoDoctorForm;
import org.example.Forms.admin.documents.modal.*;
import org.example.entity.Doctor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentsForm extends JPanel{
    private JButton doctorsButton;
    private JButton diseaseButton;
    private JButton patientsButton;
    private JButton patientsByDoctorButton;
    private JPanel panel;
    private JButton doctorsByDiseaseButton;
    private JButton appointedButton;
    private JButton dataBaseButton;
    JFrame frame;

    public DocumentsForm(JFrame frame){
        this.frame = frame;
        add(panel);
        setSize(-1, -1);
        setVisible(true);
        doctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDoctorsDocument();
            }
        });
        patientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPatientsDocument();
            }
        });
        diseaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDiseaseDocument();
            }
        });
        patientsByDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPatientsByDoctorDocument();
            }
        });
        doctorsByDiseaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDoctorsByDiseaseButtonDocument();
            }
        });
        appointedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAppointedButtonDocument();
            }
        });
        dataBaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDataBaseButtonDocument();
            }
        });
    }
    public void createDoctorsDocument(){
        ListDoctorsForm.showDialog(frame);
        updateForm();
    }
    public void createPatientsDocument(){
        ListPatientForm.showDialog(frame);
        updateForm();
    }
    public void createDiseaseDocument(){
        ListDiseaseForm.showDialog(frame);
        updateForm();
    }
    public void createPatientsByDoctorDocument(){
        ListPatientsByDoctorForm.showDialog(frame);
        updateForm();
    }
    public void createDoctorsByDiseaseButtonDocument(){
        ListDoctorsByDiseaseForm.showDialog(frame);
        updateForm();
    }
    public void createAppointedButtonDocument(){
        ListPatientByDate.showDialog(frame);
        updateForm();
    }
    public void createDataBaseButtonDocument(){
        DataBaseStateForm.showDialog(frame);
        updateForm();
    }

    public void updateForm(){

    }
}

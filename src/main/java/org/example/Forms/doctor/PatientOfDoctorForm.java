package org.example.Forms.doctor;

import org.example.BD;
import org.example.Forms.admin.patient.modal.InfoPatientForm;
import org.example.Forms.doctor.modal.AddAppointDoctorForm;
import org.example.Forms.doctor.modal.AddProfDoctorForm;
import org.example.MainForm;
import org.example.entity.Appointed;
import org.example.entity.Doctor;
import org.example.entity.MedUser;
import org.example.entity.Patient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientOfDoctorForm extends JPanel{
    private JButton profButton;
    private JButton infoButton;
    private JList list1;
    private JPanel panel;
    JFrame frame;
    private JButton appointButton;
    List<Patient> patients = new ArrayList<>();

    public PatientOfDoctorForm(JFrame frame){
        this.frame = frame;
        add(panel);
        setSize(-1, -1);
        setVisible(true);
        updateButton();
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoDoctorForm();
            }
        });
        profButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profDoctorForm();
            }
        });
        appointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appointDoctorForm();
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateButton();
            }
        });
    }
    public void updateList(){
        MedUser user = MainForm.getCurUser();
        if(user == null)
            return;
        Doctor doctor = BD.getDoctorById(user.targetId);
        patients = BD.getPatientsByDoctor(doctor.id);
        String pattern = "%-40s %-20";
        List<String> names = patients.stream().map(d -> d.name + " [" + d.disease.name + "]").collect(Collectors.toList());
        list1.setListData(names.toArray());
    }
    public void updateForm(){
        updateList();
        updateButton();
    }
    public void updateButton(){
        if(list1.getSelectedIndex() >= 0){
            infoButton.setEnabled(true);
            profButton.setEnabled(true);
            appointButton.setEnabled(true);
        }else{
            infoButton.setEnabled(false);
            profButton.setEnabled(false);
            appointButton.setEnabled(false);
        }
    }
    public void profDoctorForm(){
        AddProfDoctorForm.showDialog(frame, patients.get(list1.getSelectedIndex()));
    }
    public void infoDoctorForm(){
        InfoPatientForm.showDialog(frame, patients.get(list1.getSelectedIndex()));
    }
    public void appointDoctorForm(){
        AddAppointDoctorForm.showDialog(frame, patients.get(list1.getSelectedIndex()));
    }
}

package org.example.Forms.admin.doctor;

import org.example.BD;
import org.example.Forms.JPanelUpdate;
import org.example.Forms.admin.doctor.modal.AddDoctorForm;
import org.example.Forms.admin.doctor.modal.ChangeDoctorForm;
import org.example.Forms.admin.doctor.modal.DeleteDoctorForm;
import org.example.Forms.admin.doctor.modal.InfoDoctorForm;
import org.example.entity.Doctor;
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

public class DoctorControllerForm extends JPanel {
    private JList list1;
    private JButton addButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JButton infoButton;
    private JPanel panel;
    private JFrame frame;
    private List<Doctor> doctors = new ArrayList<>();

    public DoctorControllerForm(JFrame frame){
        this.frame = frame;
        add(panel);
        setSize(-1, -1);
        updateList();
        setVisible(true);
        updateButton();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDoctorForm();
            }
        });
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoDoctorForm();
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDoctorForm();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDoctorForm();
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateButton();
            }
        });
    }
    public void updateButton(){
        if(list1.getSelectedIndex() >= 0){
            changeButton.setEnabled(true);
            deleteButton.setEnabled(true);
            infoButton.setEnabled(true);
        }else{
            changeButton.setEnabled(false);
            deleteButton.setEnabled(false);
            infoButton.setEnabled(false);
        }
    }
    public void addDoctorForm(){
        AddDoctorForm.showDialog(frame);
        updateForm();
    }
    public void deleteDoctorForm(){
        Doctor doctor =  doctors.get(list1.getSelectedIndex());
        DeleteDoctorForm.showDialog(frame, doctor);
        updateForm();
    }
    public void changeDoctorForm(){
        Doctor doctor =  doctors.get(list1.getSelectedIndex());
        ChangeDoctorForm.showDialog(frame, doctor);
        updateForm();
    }
    public void infoDoctorForm(){
        Doctor doctor =  doctors.get(list1.getSelectedIndex());
        InfoDoctorForm.showDialog(frame, doctor);
        updateForm();
    }
    public void updateList(){
        doctors = BD.getAllDoctors();
        List<String> names = doctors.stream().map(d -> d.profession.name + ": " + d.name).collect(Collectors.toList());
        list1.setListData(names.toArray());
    }

    public void updateForm(){
        updateButton();
        updateList();
    }
}

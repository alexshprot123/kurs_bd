package org.example.Forms.admin.documents.modal;

import org.example.BD;
import org.example.DocumentWriter;
import org.example.entity.Doctor;
import org.example.entity.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class ListPatientForm extends JDialog{
    private JTextField nameTextField;
    private JButton backButton;
    private JButton okButton;
    private JLabel errorMessageLabel;
    private JPanel panel;

    public ListPatientForm(JFrame parent){
        super(parent, "", true);
        setContentPane(panel);
        setSize(400, 200);
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
                if(createDocument())
                    dispose();
            }
        });
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
            List<Patient> patients = BD.getAllPatient();
            DocumentWriter.patientsDocument(patients, nameTextField.getText());
            return true;
        }catch (DateTimeParseException dateTimeParseException){
            System.out.println(dateTimeParseException.getMessage());
        }catch (NullPointerException nullPointerException){
        }
        return false;
    }

    public static void showDialog(JFrame parent) {
        ListPatientForm dialog = new ListPatientForm(parent);
        dialog.setVisible(true);
    }
}

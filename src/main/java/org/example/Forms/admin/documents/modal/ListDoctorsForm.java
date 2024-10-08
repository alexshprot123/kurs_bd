package org.example.Forms.admin.documents.modal;

import org.example.BD;
import org.example.DocumentWriter;
import org.example.entity.Doctor;
import org.example.entity.Profession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListDoctorsForm extends JDialog{
    private JComboBox profComboBox;
    private JButton backButton;
    private JButton okButton;
    private JPanel panel;
    private JTextField nameTextField;
    private JLabel errorMessageLabel;
    List<Profession> profs = new ArrayList<>();

    public ListDoctorsForm(JFrame parent){
        super(parent, "", true);
        setContentPane(panel);
        setSize(400, 200);
        errorMessageLabel.setVisible(false);
        profs = BD.getAllProfessions();
        List<String> profsName = profs.stream().map(p -> p.name).collect(Collectors.toList());
        profComboBox.addItem("<Все>");
        for(String prof : profsName)
            profComboBox.addItem(prof);

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
            List<Doctor> doctors = null;
            if(profComboBox.getSelectedItem().toString().equals("<Все>"))
                doctors = BD.getAllDoctors();
            else
                doctors = BD.getDoctorsByProfession(profs.get(profComboBox.getSelectedIndex()-1).id);
            DocumentWriter.doctorsDocument(doctors, nameTextField.getText());
            return true;
        }catch (DateTimeParseException dateTimeParseException){
            System.out.println(dateTimeParseException.getMessage());
        }catch (NullPointerException nullPointerException){
        }
        return false;
    }

    public static void showDialog(JFrame parent) {
        ListDoctorsForm dialog = new ListDoctorsForm(parent);
        dialog.setVisible(true);
    }
}

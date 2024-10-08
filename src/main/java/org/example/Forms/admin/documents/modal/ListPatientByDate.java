package org.example.Forms.admin.documents.modal;

import org.example.BD;
import org.example.Consts;
import org.example.DocumentWriter;
import org.example.entity.Appointed;
import org.example.entity.Patient;
import org.example.entity.Profession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class ListPatientByDate extends JDialog{
    private JPanel panel;
    private JTextField nameTextField;
    private JLabel errorMessageLabel;
    private JButton backButton;
    private JButton okButton;
    private JTextField dateTextField;

    public ListPatientByDate(JFrame parent) {
        super(parent, "", true);
        setContentPane(panel);
        setSize(400, 300);
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
            if(dateTextField.getText().isEmpty()){
                showError("Дата не указана");
                return false;
            }
            LocalDate localDate = LocalDate.parse(dateTextField.getText(), Consts.DATE_FORMAT);
            List<Appointed> appointeds = BD.getAppointedByData(localDate);

            DocumentWriter.appointedsDocument(appointeds, nameTextField.getText());
            return true;
        }catch (DateTimeParseException dateTimeParseException){
            showError("Неверно указана дата. Дата должна быть вида dd.MM.yyyy");
        }catch (NullPointerException nullPointerException){
            showError("Пустое поле");
        }
        return false;
    }

    public static void showDialog(JFrame parent) {
        ListPatientByDate dialog = new ListPatientByDate(parent);
        dialog.setVisible(true);
    }
}

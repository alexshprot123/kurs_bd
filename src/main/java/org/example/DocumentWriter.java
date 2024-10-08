package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class DocumentWriter {

    public static CellStyle getBoldStyle(Workbook workbook){
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);

        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setFont(boldFont);

        return boldStyle;
    }
    public static void doctorsDocument(List<Doctor> doctorList, String nameFile){
        try {
            String filePath = "documents\\" + nameFile + ".xlsx";
            File file = new File(filePath);

            Workbook workbook = new XSSFWorkbook();
            String[] strs = {"Специальность", "ФИО", "Пол", "Дата рождения", "Номер диплома", "Телефон", "Дата трудоустройства"};
            Sheet sheet = workbook.createSheet("Сотрудники");

            Row row = sheet.createRow(0);
            for(int i = 0; i < strs.length; i++){
                Cell c = row.createCell(i);
                c.setCellValue(strs[i]);
                c.setCellStyle(getBoldStyle(workbook));
            }

            for (int i = 0; i < doctorList.size(); i++) {
                Doctor d = doctorList.get(i);
                Row r = sheet.createRow(i + 1);
                r.createCell(0).setCellValue(d.profession.name);
                r.createCell(1).setCellValue(d.name);
                r.createCell(2).setCellValue(d.gender);
                r.createCell(3).setCellValue(d.birthdate.format(Consts.DATE_FORMAT));
                r.createCell(4).setCellValue(d.certificate);
                r.createCell(5).setCellValue(d.phone);
                r.createCell(6).setCellValue(d.startWork.format(Consts.DATE_FORMAT));
            }
            for(int i = 0; i < 8; i++)
                sheet.autoSizeColumn(i);
            row.setRowStyle(getBoldStyle(workbook));

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void patientsDocument(List<Patient> patientList, String nameFile){
        try {
            String filePath = "documents\\" + nameFile + ".xlsx";
            File file = new File(filePath);

            Workbook workbook = new XSSFWorkbook();
            String[] strs = {"ФИО", "Пол", "Дата рождения", "СНИЛС", "Телефон", "Диагноз", "Ведущий врач"};
            Sheet sheet = workbook.createSheet("Пациенты");

            Row row = sheet.createRow(0);
            for(int i = 0; i < strs.length; i++){
                Cell c = row.createCell(i);
                c.setCellValue(strs[i]);
                c.setCellStyle(getBoldStyle(workbook));
            }

            for (int i = 0; i < patientList.size(); i++) {
                Patient d = patientList.get(i);
                Row r = sheet.createRow(i + 1);
                r.createCell(0).setCellValue(d.name);
                r.createCell(1).setCellValue(d.gender);
                r.createCell(2).setCellValue(d.birthdate.format(Consts.DATE_FORMAT));
                r.createCell(3).setCellValue(d.snils);
                r.createCell(4).setCellValue(d.phone);
                r.createCell(5).setCellValue(d.disease.name);
                r.createCell(6).setCellValue(d.doctor.name);
            }
            for(int i = 0; i < strs.length; i++)
                sheet.autoSizeColumn(i);
            row.setRowStyle(getBoldStyle(workbook));

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void diseasesDocument(List<Disease> diseases, String nameFile){
        try {
            String filePath = "documents\\" + nameFile + ".xlsx";
            File file = new File(filePath);

            Workbook workbook = new XSSFWorkbook();
            String[] strs = {"Название", "Тип"};
            Sheet sheet = workbook.createSheet("Болезни");

            Row row = sheet.createRow(0);
            for(int i = 0; i < strs.length; i++){
                Cell c = row.createCell(i);
                c.setCellValue(strs[i]);
                c.setCellStyle(getBoldStyle(workbook));
            }

            for (int i = 0; i < diseases.size(); i++) {
                Disease d = diseases.get(i);
                Row r = sheet.createRow(i + 1);
                r.createCell(0).setCellValue(d.name);
                r.createCell(1).setCellValue(d.type);
            }
            for(int i = 0; i < strs.length; i++)
                sheet.autoSizeColumn(i);
            row.setRowStyle(getBoldStyle(workbook));

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void professionDocument(List<Profession> professions, String nameFile){
        try {
            String filePath = "documents\\" + nameFile + ".xlsx";
            File file = new File(filePath);

            Workbook workbook = new XSSFWorkbook();
            String[] strs = {"Название"};
            Sheet sheet = workbook.createSheet("Специальности");

            Row row = sheet.createRow(0);
            for(int i = 0; i < strs.length; i++){
                Cell c = row.createCell(i);
                c.setCellValue(strs[i]);
                c.setCellStyle(getBoldStyle(workbook));
            }

            for (int i = 0; i < professions.size(); i++) {
                Profession d = professions.get(i);
                Row r = sheet.createRow(i + 1);
                r.createCell(0).setCellValue(d.name);
            }
            for(int i = 0; i < strs.length; i++)
                sheet.autoSizeColumn(i);
            row.setRowStyle(getBoldStyle(workbook));

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void appointedsDocument(List<Appointed> appointeds, String nameFile){
        try {
            String filePath = "documents\\" + nameFile + ".xlsx";
            File file = new File(filePath);

            Workbook workbook = new XSSFWorkbook();
            String[] strs = {"Дата", "ФИО пациента", "Специальность", "ФИО врача"};
            Sheet sheet = workbook.createSheet("Записи на осмотр");

            Row row = sheet.createRow(0);
            for(int i = 0; i < strs.length; i++){
                Cell c = row.createCell(i);
                c.setCellValue(strs[i]);
                c.setCellStyle(getBoldStyle(workbook));
            }

            for (int i = 0; i < appointeds.size(); i++) {
                Appointed d = appointeds.get(i);
                Row r = sheet.createRow(i + 1);
                r.createCell(0).setCellValue(d.date);
                r.createCell(1).setCellValue(d.patient.name);
                r.createCell(2).setCellValue(d.doctor.profession.name);
                r.createCell(3).setCellValue(d.doctor.name);
            }
            for(int i = 0; i < strs.length; i++)
                sheet.autoSizeColumn(i);
            row.setRowStyle(getBoldStyle(workbook));

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void dataBaseDocument(List<Integer> sizes, String nameFile){
        try {
            String filePath = "documents\\" + nameFile + ".xlsx";
            File file = new File(filePath);

            Workbook workbook = new XSSFWorkbook();
            String[] strs = {"Болезни", "Врачи", "Пациенты", "Специальность", "Назначан", "Наблюдает"};
            Sheet sheet = workbook.createSheet("Записи на осмотр");

            Row row = sheet.createRow(0);
            for(int i = 0; i < strs.length; i++){
                Cell c = row.createCell(i);
                c.setCellValue(strs[i]);
                c.setCellStyle(getBoldStyle(workbook));
            }
            Row r = sheet.createRow(1);
            for(int i = 0; i < sizes.size(); i++){
                r.createCell(i).setCellValue(sizes.get(i));
            }

            for(int i = 0; i < strs.length; i++)
                sheet.autoSizeColumn(i);
            row.setRowStyle(getBoldStyle(workbook));

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

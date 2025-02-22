package eventos;

import arreglos.ArregloAlumno;
import clases.Alumno;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ReporteAlummnosVigentesEvent implements ComponentsSetters{
    private JButton btnGenerar;
    private JButton btnGenerarExcel;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;
    ArregloAlumno ArregloA;

    public ReporteAlummnosVigentesEvent(ArregloAlumno ArregloA){
        this.ArregloA = ArregloA;


    }
    public void actionPerformedBtnMostrar(ActionEvent e) {
        System.out.println(ArregloA);
        listar();

    }

    public void actionPerformedBtnGenerarExcel(ActionEvent e) {
        generarExcel();
    }
    void listar() {
        tableModel.setRowCount(0);
        for (int i = 0; i < ArregloA.tamano(); i++) {
            Alumno a = ArregloA.obtener(i);
            if (a.getEstado() == 1) {
                tableModel.addRow(new Object[] {
                        a.getCodAlumno(),
                        a.getNombre(),
                        a.getApellido(),
                        a.getDni(),
                        a.getEdad(),
                        a.getCelular()
                });

            }

        }
        btnGenerarExcel.setEnabled(tableModel.getRowCount() > 0);
    }

    void generarExcel() {
        String folderPath = "src/doc/reportes";
        String filePath = folderPath + File.separator + "ReporteAlumnosVigentes.xlsx";
        Workbook workbook;
        Sheet sheet;
        boolean fileExists = new File(filePath).exists();

        try {

            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            if (fileExists) {
                FileInputStream fis = new FileInputStream(filePath);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
                int lastRow = sheet.getLastRowNum();
                for (int i = 1; i <= lastRow; i++) {
                    sheet.removeRow(sheet.getRow(i));
                }
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Alumnos Vigentes");
            }

            if (!fileExists || sheet.getRow(0) == null) {
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Código del Alumno");
                headerRow.createCell(1).setCellValue("Nombres");
                headerRow.createCell(2).setCellValue("Apellidos");
                headerRow.createCell(3).setCellValue("DNI");
                headerRow.createCell(4).setCellValue("Edad");
                headerRow.createCell(5).setCellValue("Celular");
            }

            ArregloAlumno ArregloA = new ArregloAlumno();
            int rowNum = 1;
            for (int i = 0; i < ArregloA.tamano(); i++) {
                Alumno a = ArregloA.obtener(i);
                if (a.getEstado() == 1) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(a.getCodAlumno());
                    row.createCell(1).setCellValue(a.getNombre());
                    row.createCell(2).setCellValue(a.getApellido());
                    row.createCell(3).setCellValue(a.getDni());
                    row.createCell(4).setCellValue(a.getEdad());
                    row.createCell(5).setCellValue(a.getCelular());
                }
            }

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("Archivo Excel generado/actualizado exitosamente.");
        } catch (IOException ex) {
            System.out.println("Error al generar el archivo Excel: " + ex.getMessage());
        }
    }
    @Override
    public void setComponent(HashMap<String, JComponent> componentsMap) {
        this.btnGenerar = (JButton) componentsMap.get("btnGenerar");
        this.btnGenerarExcel = (JButton) componentsMap.get("btnGenerarExcel");
        this.scrollPane = (JScrollPane) componentsMap.get("scrollPane");
    }

    public void setTable(JTable table){
        this.table = table;
    }
    public void setTableModel(DefaultTableModel tableModel){
        this.tableModel = tableModel;
    }
}

package eventos;

import arreglos.ArregloAlumno;
import arreglos.ArregloCurso;
import arreglos.ArregloMatricula;
import clases.Alumno;
import clases.Curso;
import clases.Matricula;
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

public class ReporteMatriculasCursoEvent implements ComponentsSetters {
    private JButton btnMostrar;
    private JButton btnGenerarExcel;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JDialog dialog;
    private final ArregloMatricula ArregloM;
    private final ArregloAlumno ArregloA;
    private final ArregloCurso ArregloC;

    public ReporteMatriculasCursoEvent(ArregloMatricula ArregloM,
                                       ArregloAlumno ArregloA,
                                       ArregloCurso ArregloC){
        this.ArregloM = ArregloM;
        this.ArregloA = ArregloA;
        this.ArregloC = ArregloC;


    }

    public void actionPerformedBtnMostrar(ActionEvent e) {
        listar();
    }

    public void actionPerformedBtnGenerarExcel(ActionEvent e) {
        generarExcel();
    }
    void listar() {
        tableModel.setRowCount(0);

        for (int i = 0; i < ArregloM.tamano(); i++) {
            if (ArregloM.tamano() == 0) {
                return;
            } else {
                Matricula m = ArregloM.obtener(i);
                Alumno a = ArregloA.buscar(m.getCodAlumno());
                Curso c = ArregloC.buscar(m.getCodCurso());


                tableModel.addRow(
                        new Object[] {
                                m.getCodMatricula(),
                                a.getNombre(),
                                a.getApellido(),
                                c.getAsignatura()
                        });
            }
        }
        btnGenerarExcel.setEnabled(tableModel.getRowCount() > 0);
    }
    void generarExcel() {
        String folderPath = "src/doc/reportes";
        String filePath = folderPath + File.separator + "ReporteMatriculasPorCurso.xlsx";

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
                sheet = workbook.createSheet("Matrículas por Curso");
            }

            if (!fileExists || sheet.getRow(0) == null) {
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("N° MATRICULA");
                headerRow.createCell(1).setCellValue("NOMBRE");
                headerRow.createCell(2).setCellValue("APELLIDO");
                headerRow.createCell(3).setCellValue("CURSO");
            }

            ArregloMatricula ArregloM = new ArregloMatricula();
            ArregloAlumno ArregloA = new ArregloAlumno();
            ArregloCurso ArregloC = new ArregloCurso();
            int rowNum = 1;

            for (int i = 0; i < ArregloM.tamano(); i++) {
                Matricula m = ArregloM.obtener(i);
                Alumno a = ArregloA.buscar(m.getCodAlumno());
                Curso c = ArregloC.buscar(m.getCodCurso());

                if (a != null && c != null) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(m.getCodMatricula());
                    row.createCell(1).setCellValue(a.getNombre());
                    row.createCell(2).setCellValue(a.getApellido());
                    row.createCell(3).setCellValue(c.getAsignatura());
                }
            }

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("Archivo Excel guardado en: " + filePath);
        } catch (IOException ex) {
            System.out.println("Error al generar el archivo Excel: " + ex.getMessage());
        }
    }

    @Override
    public void setComponent(HashMap<String, JComponent> componentsMap) {
        this.btnMostrar = (JButton) componentsMap.get("btnMostrar");
        this.btnGenerarExcel = (JButton) componentsMap.get("btnGenerarExcel");
        this.scrollPane = (JScrollPane) componentsMap.get("scrollPane");
    }
    public void setDialog(JDialog dialog){
        this.dialog = (JDialog) dialog;
    }
    public void setDefaultTableModel(DefaultTableModel tableModel){
        this.tableModel = tableModel;
    }
    public void setTableModel(JTable table){
        this.table = table;
    }


}

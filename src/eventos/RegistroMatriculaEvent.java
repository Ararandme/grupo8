package eventos;

import arreglos.ArregloAlumno;
import arreglos.ArregloCurso;
import arreglos.ArregloMatricula;
import clases.Alumno;
import clases.Curso;
import clases.Matricula;
import clases.Retiro;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import libreria.Fecha;
import libreria.Lib;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RegistroMatriculaEvent implements ComponentsSetters {

    JComboBox cboCodCurso;
    JComboBox<String> cboCodAlumno;
    JButton btnBuscar;
    JButton btnEliminar;
    JButton btnEditar;
    JButton btnConsultar;
    JButton btnRegistrar;
    JButton btnConfirmar;
    JTextField txtMatricula;
    JTextField txtNomAlumno;
    JTextField txtNomAsignatura;

    ArregloMatricula ArregloM;
    ArregloAlumno ArregloA;
    ArregloCurso ArregloC;

    int cboA;
    int cboC;
    private JDialog dialog;
    private DefaultTableModel modelo;
    private JTable tblMatricula;

    public RegistroMatriculaEvent(ArregloMatricula ArregloM,ArregloAlumno ArregloA,ArregloCurso ArregloC){

        this.ArregloM = ArregloM;
        this.ArregloA = ArregloA;
        this.ArregloC = ArregloC;
    }



    public void setCboCodCursoEvent() {
        System.out.println("asd");
        cboC = Integer.parseInt((String) cboCodCurso.getSelectedItem());
        txtNomAsignatura.setText(Mcurso());
    }

    public void setCboCodAlumnoEvent() {
        cboA = Integer.parseInt((String) cboCodAlumno.getSelectedItem());
        txtNomAlumno.setText(Malumno());
    }

    public void setBtnBuscarEvent() {
        // Deshabilita los botones y campos que no se deben usar durante la búsqueda
        txtMatricula.setEnabled(false);
        btnRegistrar.setEnabled(false);
        btnConfirmar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        try {
            // Lee el código de la matrícula que se desea buscar
            int codigoMatricula = leerCodigoMatricula();

            Matricula m = ArregloM.buscar(codigoMatricula);

            // Si la matrícula existe, muestra los datos del alumno y curso
            if (m != null) {
                Alumno alumno = ArregloA.buscar(m.getCodAlumno());
                Curso curso = ArregloC.buscar(m.getCodCurso());

                if (alumno != null && curso != null) {
                    txtNomAlumno.setText(alumno.getNombre() + " " + alumno.getApellido());
                    txtNomAsignatura.setText(curso.getAsignatura());

                    cboCodAlumno.setSelectedItem(String.valueOf(alumno.getCodAlumno()));
                    cboCodCurso.setSelectedItem(String.valueOf(curso.getCodCurso()));

                    txtMatricula.setEnabled(true);

                    // Habilita los botones de editar y eliminar
                    btnEditar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                } else {
                    error("Datos incompletos para la matrícula ingresada.", txtMatricula);
                    txtMatricula.setEnabled(true);
                }
            } else {
                error("El código de matrícula ingresado no existe.", txtMatricula);
                txtMatricula.setEnabled(true);
            }
        } catch (NumberFormatException e4) {
            error("Ingrese un código de matrícula válido.", txtMatricula);
            txtMatricula.setEnabled(true);
        }
    }

    public void setBtnEliminarEvent() {
        try {
            // Lee el código de la matrícula que se desea eliminar
            int codigoMatricula = leerCodigoMatricula();

            Matricula matriculaAEliminar = ArregloM.buscar(codigoMatricula);

            // Si la matrícula existe, verifica si el alumno está retirado
            if (matriculaAEliminar != null) {
                Alumno alumno = ArregloA.buscar(matriculaAEliminar.getCodAlumno());

                if (alumno.getEstado() == 2) {
                    error("No se puede eliminar la matrícula porque el alumno está retirado.", txtMatricula);
                    return;
                }

                // Solicita confirmación para eliminar la matrícula
                int respuesta = confirmar("¿Está seguro de que desea eliminar esta matrícula?");
                if (respuesta == JOptionPane.YES_OPTION) {
                    ArregloM.eliminar(matriculaAEliminar);
                    informacion("Matrícula eliminada exitosamente.");
                    limpiar();
                }
            } else {
                error("La matrícula que desea eliminar no existe.", txtMatricula);
            }
        } catch (Exception e3) {
            error("Ocurrió un error al eliminar la matrícula: " + e3.getMessage(), txtMatricula);
        }
    }

    public void setBtnEditarEvent() {
        txtMatricula.setEnabled(false);
        txtNomAlumno.setEnabled(false);
        txtNomAsignatura.setEditable(false);
        cboCodAlumno.setEnabled(false);
        cboCodCurso.setEnabled(true);

        btnRegistrar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnConfirmar.setEnabled(true);
        btnConsultar.setEnabled(false);
        btnEliminar.setEnabled(true);
    }

    public void setBtnConsultarEvent() {
        txtMatricula.setEnabled(true);
        txtMatricula.requestFocus();
        cboCodAlumno.setEnabled(false);
        cboCodCurso.setEnabled(false);

        txtMatricula.setText("");
        cboCodAlumno.requestFocus();
        btnBuscar.setEnabled(true);
        btnRegistrar.setEnabled(false);
        btnConsultar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnConfirmar.setEnabled(false);
        btnEliminar.setEnabled(false);

        listar();
    }

    public void setBtnRegistrarEvent() {
        try {
            // Lee el código de alumno y curso seleccionados
            int codAlumno = leerCodigoAlumno();
            int codCurso = leerNumeroCurso();

            // Busca el alumno y el curso en los arreglos correspondientes
            Alumno a = ArregloA.buscar(codAlumno);
            Curso c = ArregloC.buscar(codCurso);

            // Verifica si el alumno o el curso no existen
            if (a == null || c == null) {
                error("El código del alumno o curso no ha sido registrado", txtNomAlumno);
                return;
            }

            // Lee el código de matrícula y verifica si ya está registrado
            int numMatricula = leerCodigoMatricula();
            Matricula mExistente = ArregloM.buscar(numMatricula);

            // Si la matrícula ya existe, muestra un mensaje de error
            if (mExistente != null) {
                error("El código de matrícula ingresado ya está registrado", txtMatricula);
                return;
            }

            // Verifica si el alumno ya tiene una matrícula registrada
            Matricula m = ArregloM.buscarAlumno(codAlumno);
            if (m != null) {
                informacion("El alumno ya ha sido matriculado");
                return;
            }

            // Muestra los datos del alumno y curso y pregunta al usuario si desea continuar con la matrícula
            String msg = obtenerDatosAlumno() + "\n\n" + obtenerDatosCurso();
            int opcion = confirmar("¿Desea matricular al alumno?\n\n" + msg);

            // Si el usuario confirma, se registra la matrícula
            if (opcion == JOptionPane.YES_OPTION) {
                Matricula nuevo = new Matricula(numMatricula, Fecha.fechaActual(), Fecha.horaActual(), codAlumno, codCurso, 0);
                ArregloM.adicionar(nuevo);

                // Actualiza el estado del alumno a "matriculado" (estado 1)
                a.setEstado(1);
                ArregloA.actualizarArchivo();

                // Genera un PDF con los detalles de la matrícula
                generarPDF(nuevo, a, c);

                limpiar();
                listar();
                informacion("Matrícula registrada correctamente.");
            }
        } catch (Exception ex) {
            error("Error al registrar la matrícula: " + ex.getMessage(), txtNomAlumno);
        }
    }

    public void setBtnConfirmarEvent() {
        int numMatricula = leerCodigoMatricula();
        try {
            // Lee los códigos de alumno y curso seleccionados
            int codAlumno = leerCodigoAlumno();
            try {
                int codCurso = leerNumeroCurso();

                if (btnConfirmar.isEnabled()) {
                    // Busca la matrícula y actualiza los datos del alumno y curso
                    Curso c = ArregloC.buscar(codCurso);
                    Matricula m = ArregloM.buscar(numMatricula);
                    if (m != null) {
                        m.setCodAlumno(codAlumno);
                        m.setCodCurso(codCurso);
                        txtNomAsignatura.setText(c.getAsignatura());
                        ArregloM.actualizarArchivo();

                        // Actualiza los botones y campos para finalizar la edición
                        btnBuscar.setEnabled(false);
                        btnRegistrar.setEnabled(true);
                        btnConsultar.setEnabled(true);
                        btnEditar.setEnabled(false);
                        btnConfirmar.setEnabled(false);
                        btnEliminar.setEnabled(false);
                    } else {
                        error("La matrícula no existe. Verifique el código ingresado.", txtMatricula);
                    }
                }
                listar();
            } catch (Exception e1) {
                error("Ingrese el codigo del curso", txtNomAsignatura);
            }
        } catch (Exception e2) {
            error("Ingrese el codigo del alumno", txtNomAlumno);
        }
    }
    public void generarPDF(Matricula matricula, Alumno alumno, Curso curso) {
        try {
            // to do , create a the Docment class
            String folderPath = "src/doc/comprobantes";
            File folder = new File(folderPath);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String destino = folderPath + "/Matricula_" + matricula.getCodMatricula() + ".pdf";
            File archivoPDF = new File(destino);

            if (archivoPDF.exists()) {
                archivoPDF.delete();
            }

            PdfWriter writer = new PdfWriter(destino);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(50, 50, 50, 50);

            PdfFont fontTitulo = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontTexto = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            document.add(new Paragraph("REGISTRO DE MATRÍCULA").setFont(fontTitulo).setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("\nDatos del Alumno:").setFont(fontTitulo).setFontSize(14));
            document.add(new Paragraph("Código: " + alumno.getCodAlumno()).setFont(fontTexto));
            document.add(new Paragraph("Nombre: " + alumno.getNombre()).setFont(fontTexto));
            document.add(new Paragraph("Apellido: " + alumno.getApellido()).setFont(fontTexto));
            document.add(new Paragraph("DNI: " + alumno.getDni()).setFont(fontTexto));
            document.add(new Paragraph("Edad: " + alumno.getEdad()).setFont(fontTexto));
            document.add(new Paragraph("Celular: " + alumno.getCelular()).setFont(fontTexto));
            document.add(new Paragraph("Estado: " + alumno.getEstado()).setFont(fontTexto));

            document.add(new Paragraph("\nDatos del Curso:").setFont(fontTitulo).setFontSize(14));
            document.add(new Paragraph("Código: " + curso.getCodCurso()).setFont(fontTexto));
            document.add(new Paragraph("Asignatura: " + curso.getAsignatura()).setFont(fontTexto));
            document.add(new Paragraph("Ciclo: " + curso.getCiclo()).setFont(fontTexto));
            document.add(new Paragraph("Créditos: " + curso.getCreditos()).setFont(fontTexto));

            document.add(new Paragraph("\n"));
            float[] columnWidths = {100f, 200f, 200f};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("Código Matrícula").setFont(fontTitulo))
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha de Matrícula").setFont(fontTitulo))
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Hora de Registro").setFont(fontTitulo))
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));

            table.addCell(
                    new Cell().add(new Paragraph(String.valueOf(matricula.getCodMatricula())).setFont(fontTexto)));
            table.addCell(new Cell().add(new Paragraph(matricula.getFecha()).setFont(fontTexto)));
            table.addCell(new Cell().add(new Paragraph(matricula.getHora()).setFont(fontTexto)));

            document.add(table);

            document.add(new Paragraph("\n\n").setFont(fontTitulo).setFontSize(14));
            document.add(new Paragraph("\n__________________________").setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Firma del Responsable").setTextAlignment(TextAlignment.CENTER));

            document.close();
            JOptionPane.showMessageDialog(null, "/Matricula_" + matricula.getCodMatricula() + ".pdf");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage());
        }
    }

    int leerCodigoMatricula() {
        return Integer.parseInt(txtMatricula.getText().trim());
    }

    int leerCodigoAlumno() {
        return cboA;
    }

    int leerNumeroCurso() {
        return cboC;
    }

    String obtenerDatosAlumno() {
        ArregloAlumno ArregloA = new ArregloAlumno();
        Alumno a = ArregloA.buscar(leerCodigoAlumno());
        return "Nombres :  " + a.getNombre() + "\n" + "Apellidos :  " + a.getApellido();
    }

    String obtenerDatosCurso() {
        ArregloCurso ArregloC = new ArregloCurso();
        Curso c = ArregloC.buscar(leerNumeroCurso());
        return "Curso :  " + c.getAsignatura();
    }

    String Malumno() {
        ArregloAlumno ArregloA = new ArregloAlumno();
        Alumno a = ArregloA.buscar(cboA);
        return a.getNombre() + "\n\n" + a.getApellido();
    }

    String Mcurso() {
        ArregloCurso ArregloC = new ArregloCurso();
        Curso c = ArregloC.buscar(cboC);
        return c.getAsignatura();
    }

    void listar() {

        modelo.setRowCount(0);
        Matricula m;

        for (int i = 0; i < ArregloM.tamano(); i++) {
            m = ArregloM.obtener(i);
            Alumno a = ArregloA.buscar(m.getCodAlumno());
            Curso c = ArregloC.buscar(m.getCodCurso());

            if (a != null && c != null) {
                modelo.addRow(new Object[]{m.getCodMatricula(), m.getFecha(), m.getHora(),
                        a.getNombre() + " " + a.getApellido(), c.getAsignatura(),
                        Lib.estadosmatricula[m.getEstado()]});
            }
        }
    }

    void limpiar() {
        txtMatricula.setText("" + ArregloM.codigoCorrelativo());
        txtNomAlumno.setText("");
        txtNomAsignatura.setText("");
        DefaultTableModel matricula = (DefaultTableModel) tblMatricula.getModel();
        matricula.setRowCount(0);
    }

    void informacion(String msg) {
        JOptionPane.showMessageDialog(dialog, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    void error(String msg, JTextField txt) {
        JOptionPane.showMessageDialog(dialog, msg, "Error", JOptionPane.ERROR_MESSAGE);

    }

    int confirmar(String msg) {
        return JOptionPane.showConfirmDialog(dialog, msg, "Confirmar", JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void setComponent(HashMap<String, JComponent> componentsMap) {

    this.btnBuscar = (JButton) componentsMap.get("btnBuscar");
    this.btnEliminar = (JButton) componentsMap.get("btnEliminar");
    this.btnEditar = (JButton) componentsMap.get("btnEditar");
    this.btnConsultar = (JButton) componentsMap.get("btnConsultar");
    this.btnRegistrar = (JButton) componentsMap.get("btnRegistrar");
    this.btnConfirmar = (JButton) componentsMap.get("btnConfirmar");
    this.txtNomAlumno = (JTextField) componentsMap.get("txtNomAlumno");
    this.txtMatricula = (JTextField) componentsMap.get("txtMatricula");
    this.txtNomAsignatura = (JTextField) componentsMap.get("txtNomAsignatura");


    }

    public void setComboBox(JComboBox<String> cbo1,JComboBox<String>  cbo2){
        this.cboCodCurso = cbo1;
        this.cboCodAlumno = cbo2;

    }
    public void setDefaultTable(DefaultTableModel defaultTable){
        this.modelo = defaultTable;
    }
    public void setTable(JTable table){
        this.tblMatricula = table;
    }
}

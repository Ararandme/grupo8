package eventos;

import arreglos.ArregloAlumno;
import arreglos.ArregloCurso;
import clases.Alumno;
import clases.Curso;
import libreria.Lib;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class ConsultaAlumnosCursoEvent implements ComponentsSetters{

    private final ArregloAlumno ArregloA = new ArregloAlumno();
    private final ArregloCurso ArregloC = new ArregloCurso();
    private JDialog dialog;
    private HashMap<String, JComponent> componentsMap;
    private DefaultTableModel alumnoTableModel;
    private DefaultTableModel cursosTableModel;

    public void setBtnBuscar(ActionEvent e){

        JTextField txtCodAlumno = (JTextField) componentsMap.get("txtCodAlumno");
        JTextField txtCodCurso = (JTextField) componentsMap.get("txtCodCurso");

        String codAlumnoText = txtCodAlumno.getText().trim();
        String codCursoText = txtCodCurso.getText().trim();

        if (!codAlumnoText.isEmpty()) {
            try {
                int codigoAlumno = Integer.parseInt(codAlumnoText);
                Alumno a = ArregloA.buscar(codigoAlumno);
                if (a != null) {
                    listarAlumno(a);
                } else {
                    error("Alumno no encontrado.", txtCodAlumno);
                }
            } catch (NumberFormatException ex) {
                error("Codigo de alumno debe ser numérico.", txtCodAlumno);
            }
        }

        if (!codCursoText.isEmpty()) {
            try {
                int codigoCurso = Integer.parseInt(codCursoText);
                Curso c = ArregloC.buscar(codigoCurso);
                if (c != null) {
                    listarCurso(c);
                } else {
                    error("Curso no encontrado.", txtCodCurso);
                }
            } catch (NumberFormatException ex) {
                error("Codigo de curso debe ser numerico.", txtCodCurso);
            }
        }
    }
    private void listarAlumno(Alumno a) {

        alumnoTableModel.setRowCount(0);
        alumnoTableModel.addRow(new Object[] {
                a.getCodAlumno(),
                a.getNombre(),
                a.getApellido(),
                a.getDni(),
                a.getEdad(),
                a.getCelular(),
                Lib.estadoAlumno[a.getEstado()] });
    }

    private void listarCurso(Curso c) {

        cursosTableModel.setRowCount(0);
        cursosTableModel.addRow(new Object[] {
                c.getCodCurso(),
                c.getAsignatura(),
                Lib.cicloCurso[c.getCiclo()],
                c.getCreditos(),
                c.getHoras() });
    }

    private void error(String s, JTextField txt) {
        mensaje(s);
        txt.setText("");
        txt.requestFocus();
    }

    private void mensaje(String s) {
        JOptionPane.showMessageDialog(dialog, s, "Informacion", JOptionPane.ERROR_MESSAGE);
    }

    public void setDialog(JDialog dialog) {
        this.dialog = dialog;
    }
    public void setTable(DefaultTableModel alumnoTableModel, DefaultTableModel cursosTableModel){
        this.alumnoTableModel = alumnoTableModel;
        this.cursosTableModel = cursosTableModel;

    }
    @Override
    public void setComponent(HashMap<String, JComponent> componentsMap) {
        this.componentsMap = componentsMap;
    }
}

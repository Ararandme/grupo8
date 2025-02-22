package eventos;

import arreglos.ArregloAlumno;
import arreglos.ArregloCurso;
import arreglos.ArregloMatricula;
import arreglos.ArregloRetiro;
import clases.Alumno;
import clases.Curso;
import clases.Matricula;
import clases.Retiro;
import libreria.Lib;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class ConsultaMatriculasRetirosEvent implements ComponentsSetters{

    private final ArregloMatricula ArregloM;
    private final ArregloAlumno ArregloA;
    private final ArregloCurso ArregloC;
    private final ArregloRetiro ArregloR;

    private JDialog dialog;
    private JTextField txtCodMatricula;
    private JTextField txtCodRetiro;
    private DefaultTableModel matriculaTableModel;
    private DefaultTableModel retiroTableModel;



    public ConsultaMatriculasRetirosEvent(ArregloMatricula ArregloM,
                                          ArregloAlumno ArregloA,
                                          ArregloCurso ArregloC,
                                          ArregloRetiro ArregloR){
        this.ArregloM = ArregloM;
        this.ArregloA = ArregloA;
        this.ArregloC = ArregloC;
        this.ArregloR = ArregloR;

    }






    void error(String s, JTextField txt) {
        mensaje(s);
        txt.setText("");
        txt.requestFocus();
    }

    void mensaje(String s) {
        JOptionPane.showMessageDialog(dialog, s, "Informacion", JOptionPane.ERROR_MESSAGE);
    }

    private void setEvent(){

        String codMatriculaText = txtCodMatricula.getText().trim();
        String codRetiroText = txtCodRetiro.getText().trim();

        if (!codMatriculaText.isEmpty()) {
            try {
                int codigoMatricula = Integer.parseInt(codMatriculaText);
                Matricula m = ArregloM.buscar(codigoMatricula);
                if (m != null) {
                    listarMatricula(m);
                } else {
                    error("Matrícula no encontrada.", txtCodMatricula);
                }
            } catch (NumberFormatException ex) {
                error("El número de matrícula debe ser numérico.", txtCodMatricula);
            }
        }

        if (!codRetiroText.isEmpty()) {
            try {
                int codigoRetiro = Integer.parseInt(codRetiroText);
                Retiro r = ArregloR.buscar(codigoRetiro);
                if (r != null) {
                    listarRetiro(r);
                } else {
                    error("Retiro no encontrado.", txtCodRetiro);
                }
            } catch (NumberFormatException ex) {
                error("El número de retiro debe ser numérico.", txtCodRetiro);
            }
        }
    }
    void listarMatricula(Matricula m) {
        matriculaTableModel.setRowCount(0);
        Alumno a = ArregloA.buscar(m.getCodAlumno());
        System.out.println(ArregloC.buscar(1212));
        Curso c = ArregloC.buscar(m.getCodCurso());

        matriculaTableModel.addRow(new Object[] {
                m.getCodMatricula(),
                Lib.estadosmatricula[m.getEstado()],
                a.getCodAlumno(),
                a.getNombre(),
                a.getApellido(),
                a.getDni(),
                a.getEdad(),
                a.getCelular(),
                c.getCodCurso(),
                c.getAsignatura(),
                Lib.cicloCurso[c.getCiclo()],
                c.getCreditos(),
                c.getHoras()
        });
    }

    void listarRetiro(Retiro r) {
        retiroTableModel.setRowCount(0);
        Matricula m = ArregloM.buscar(r.getNumMatricula());

        Alumno a = ArregloA.buscar(m.getCodAlumno());
        Curso c = ArregloC.buscar(m.getCodCurso());

        retiroTableModel.addRow(new Object[] {
                r.getNumRetiro(),
                r.getNumMatricula(),
                a.getCodAlumno(),
                a.getNombre(),
                a.getApellido(),
                a.getDni(),
                a.getEdad(),
                a.getCelular(),
                c.getCodCurso(),
                c.getAsignatura(),
                Lib.cicloCurso[c.getCiclo()],
                c.getCreditos(),
                c.getHoras()});
    }

    public void setDialog(JDialog dialog){
        this.dialog = dialog;
    }

    public void setDefaultTable(DefaultTableModel matricula, DefaultTableModel retiro) {
        this.matriculaTableModel = matricula;
        this.retiroTableModel = retiro;
    }
    public void
    setBtnEvent(ActionEvent e){
        setEvent();
    }

    @Override
    public void setComponent(HashMap<String, JComponent> componentsMap) {
        HashMap<String,JComponent> map = new HashMap<>();
        this.txtCodMatricula = (JTextField) componentsMap.get("txtCodMatricula");
        this.txtCodRetiro = (JTextField) componentsMap.get("txtCodRetiro");
    }
}

package eventos;

import arreglos.ArregloAlumno;
import arreglos.ArregloCurso;
import arreglos.ArregloMatricula;
import clases.Curso;
import clases.Matricula;
import libreria.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class MantenimientoCursoEvent implements ComponentsSetters{



    private JTextField txtCodigo;
    private JTextField txtAsignatura ;
    private JTextField txtCreditos ;
    private JTextField txtHoras ;
    private JComboBox<String> cboCiclo ;
    private JComboBox<String> cboEstado;
    private JButton btnRegistrar;
    private JButton btnConfirmar;
    private JButton btnEliminar;
    private JButton btnEditar;
    private JButton btnConsultar;
    private JButton btnBuscar;

    private DefaultTableModel modelo;

    private JDialog dialog;
    private ArregloCurso ArregloC;
    private ArregloMatricula ArregloM;

    public MantenimientoCursoEvent(ArregloCurso ArregloC, ArregloMatricula ArregloMat){

        this.ArregloC = ArregloC;

    }



    public void setEventBtnRegistrar(ActionEvent e) {

        try {

            int codigo = leerCodigoCurso();
            String asignatura = leerAsignatura();

            Curso alumnoExistente = ArregloC.buscar(codigo);
            Curso asignaturaExistente = ArregloC.buscarPorAsignatura(asignatura);

            if (alumnoExistente != null) {
                error("El codigo ingresado ya está registrado.", txtCodigo);
                return;

            }

            if (asignaturaExistente != null) {
                error("La asignatura ingresada ya está registrada.", txtAsignatura);
                return;
            }
            else {

                Curso curso = new Curso(leerCodigoCurso(), leerAsignatura(), leerCiclo(), leerCreditos(), leerHoras());

                ArregloC.adicionar(curso);
                listar();
                limpiar();
                informacion("Curso registrado correctamente.");
            }
        } catch (Exception ex) {
        }

    }

    public void setEventBtnBuscar(ActionEvent e) {

        btnEditar.setEnabled(true);
        btnConfirmar.setEnabled(false);
        btnEliminar.setEnabled(true);

        try {
            int codigo = leerCodigoCurso();
            Curso c = ArregloC.buscar(codigo);

            if (c != null) {
                txtAsignatura.setText(c.getAsignatura());
                txtHoras.setText(String.valueOf(c.getHoras()));
                txtCreditos.setText(String.valueOf(c.getCreditos()));
                cboCiclo.setSelectedIndex(c.getCiclo());
            } else {
                error("Curso no encontrado.", txtCodigo);
                btnEditar.setEnabled(false);
                btnEliminar.setEnabled(false);
            }
        } catch (Exception ex) {
            error("Error al buscar el curso: " + ex.getMessage(), txtCodigo);

        }

    }

    public void setEventBtnConsultar(ActionEvent e) {

        txtAsignatura.setEnabled(false);
        txtCodigo.setEnabled(true);
        txtCreditos.setEnabled(false);
        txtHoras.setEnabled(false);
        cboCiclo.setEnabled(false);

        txtCodigo.setText("");
        txtAsignatura.requestFocus();

        btnBuscar.setEnabled(true);
        btnRegistrar.setEnabled(false);
        btnConsultar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnConfirmar.setEnabled(false);
        btnEliminar.setEnabled(false);

        listar();
    }

    public void setEventBtnEditar(ActionEvent e) {

        txtCodigo.setEnabled(false);
        txtAsignatura.setEnabled(true);
        txtHoras.setEnabled(true);
        txtCreditos.setEnabled(true);
        cboCiclo.setEnabled(true);

        txtAsignatura.requestFocus();

        btnBuscar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnConfirmar.setEnabled(true);
    }

    public void setEventBtnConfirmar(ActionEvent e) {

        txtCodigo.setEnabled(true);
        txtAsignatura.requestFocus();

        btnRegistrar.setEnabled(true);
        btnConsultar.setEnabled(true);

        try {
            int codigo = leerCodigoCurso();
            Curso c = ArregloC.buscar(codigo);
            if (c != null) {
                c.setAsignatura(leerAsignatura());
                c.setHoras(leerHoras());
                c.setCreditos(leerCreditos());
                c.setCiclo(leerCiclo());
                ArregloC.actualizarArchivo();
                listar();
                limpiar();
                informacion("Datos actualizados correctamente.");
                txtCodigo.requestFocus();
            } else {
                error("Curso no encontrado.", txtCodigo);
            }
        } catch (Exception ex) {
            error("Error al editar: " + ex.getMessage(), txtCodigo);
            btnConfirmar.setEnabled(true);
        }

    }

    public void setEventBtnEliminar(ActionEvent e) {
        if (ArregloC.tamano() == 0)
            informacion("No hay cursos registrados");
        else {
            int codigoCurso = leerCodigoCurso();
            Matricula m = ArregloM.buscarCurso(codigoCurso);
            if (m == null) {
                int ok = confirmar("Desea eliminar el registro?");
                if (ok == 0) {
                    ArregloC.eliminar(ArregloC.buscar(codigoCurso));
                    txtCodigo.requestFocus();

                    txtCodigo.setEnabled(false);
                    txtAsignatura.setEnabled(true);
                    txtHoras.setEnabled(true);
                    txtCreditos.setEnabled(true);
                    cboCiclo.setEnabled(true);

                    txtAsignatura.requestFocus();

                    btnRegistrar.setEnabled(true);
                    btnConsultar.setEnabled(true);
                    btnBuscar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    btnEditar.setEnabled(false);
                    btnConfirmar.setEnabled(false);
                    listar();
                }
            } else
                informacion("Hay alumnos matriculados en este curso");
        }
    }

    void listar() {
        modelo.setRowCount(0);
        for (int i = 0; i < ArregloC.tamano(); i++) {
            Curso c = ArregloC.obtener(i);
            modelo.addRow(new Object[] { c.getCodCurso(), c.getAsignatura(), c.getHoras(),c.getCreditos(), TextoCiclo(c.getCiclo()) });
        }
    }

    void limpiar() {
        txtAsignatura.setText("");
        txtCodigo.setText("");
        txtHoras.setText("");
        txtCreditos.setText("");
        cboCiclo.setSelectedIndex(0);
    }

    private int leerCodigoCurso() {
        return Validador.validarCodigoCurso(txtCodigo.getText(), txtCodigo);
    }

    private String leerAsignatura() {
        return Validador.validarAsignatura(txtAsignatura.getText(), txtAsignatura);
    }

    private int leerHoras() {
        return Validador.validarHoras(txtHoras.getText(), txtHoras);
    }

    private int leerCreditos() {
        return Validador.validarCreditos(txtCreditos.getText(), txtCreditos);
    }

    private int leerCiclo() {
        return Validador.validarCiclo(cboCiclo);
    }

    String TextoCiclo(int Ciclo) {
        return cboCiclo.getItemAt(Ciclo);
    }

    private void informacion(String msg) {
        JOptionPane.showMessageDialog(dialog, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void error(String msg, JTextField txt) {
        JOptionPane.showMessageDialog(dialog, msg, "Error", JOptionPane.ERROR_MESSAGE);
        txt.requestFocus();
    }

    private int confirmar(String msg) {
        return JOptionPane.showConfirmDialog(dialog, msg, "Confirmar", JOptionPane.YES_NO_OPTION);
    }
    public void setDefaulTable(DefaultTableModel model) {
        this.modelo = model;
    }

    @Override
    public void setComponent(HashMap<String, JComponent> componentsMap) {
        this.txtHoras = (JTextField) componentsMap.get("txtHoras");
        this.txtCreditos = (JTextField) componentsMap.get("txtCreditos");
        this.txtAsignatura = (JTextField) componentsMap.get("txtAsignatura");
        this.txtCodigo = (JTextField) componentsMap.get("txtCodigo");
        this.cboCiclo = (JComboBox) componentsMap.get("cboCiclo");
        this.btnBuscar = (JButton) componentsMap.get("btnBuscar");
        this.btnEliminar = (JButton) componentsMap.get("btnEliminar");
        this.btnEditar = (JButton) componentsMap.get("btnEditar");
        this.btnConfirmar = (JButton) componentsMap.get("btnConfirmar");
        this.btnConsultar = (JButton) componentsMap.get("btnConsultar");

    }
}

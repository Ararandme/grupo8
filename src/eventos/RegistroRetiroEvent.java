package eventos;

import arreglos.ArregloAlumno;
import arreglos.ArregloCurso;
import arreglos.ArregloMatricula;
import arreglos.ArregloRetiro;
import clases.Alumno;
import clases.Curso;
import clases.Matricula;
import clases.Retiro;
import libreria.Fecha;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class RegistroRetiroEvent implements ComponentsSetters{
    private JTextField txtNumeroRetiro;
    private JTextField txtNombre;
    private JTextField txtCurso;
    private JButton btnRegistrar;
    private JButton btnConfirmar;
    private JButton btnConsultar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JComboBox<String> cboMatricula;
    private JComboBox<String> cboCodCurso;

    private DefaultTableModel modelo;
    private JTable tblRetiro;

    ArregloMatricula ArregloM;
    ArregloAlumno ArregloA;
    ArregloCurso ArregloC;
    ArregloRetiro ArregloR;

    int cboM;
    int cboC;
    private JDialog dialog;

    public RegistroRetiroEvent(ArregloMatricula ArregloM, ArregloAlumno ArregloA,ArregloCurso ArregloC,ArregloRetiro ArregloR) {
        this.ArregloM = ArregloM;
        this.ArregloA = ArregloA;
        this.ArregloC = ArregloC;
        this.ArregloR = ArregloR;
    }

    public void setEventBtnRegistrar(ActionEvent e) {
        try {
            int numeroRetiro = leerCodigoRetiro();
            int numeroMatricula = leerCodigoMatricula();

            Matricula m = ArregloM.buscar(numeroMatricula);
            String msg = obtenerDatosAlumno() + "\n\n" + obtenerDatosCurso();
            int opcion = confirmar("¿Desea desactivar la matrícula?\n\n" + msg);

            if (opcion == JOptionPane.YES_OPTION) {
                Retiro nuevo = new Retiro(numeroRetiro, numeroMatricula, Fecha.fechaActual(), Fecha.horaActual());
                ArregloR.adicionar(nuevo);
                m.setEstado(1);
                ArregloM.actualizarArchivo();

                Alumno a = ArregloA.buscar(m.getCodAlumno());
                a.setEstado(2);
                ArregloR.actualizarArchivo();

                listar();
            }
            informacion("Retiro registrado correctamente.");
        } catch (Exception ex) {
            error("Error al registrar el retiro: " + ex.getMessage(), txtNumeroRetiro);
        }
    }

    public void setEventBtnConsultar(ActionEvent e) {

        btnConsultar.setEnabled(false);
        txtNumeroRetiro.setEnabled(true);
        cboMatricula.setEnabled(false);
        cboCodCurso.setEnabled(false);

        txtNumeroRetiro.setText("");
        cboMatricula.requestFocus();
        txtNombre.setText("");
        txtCurso.setText("");

        btnBuscar.setEnabled(true);
        btnRegistrar.setEnabled(false);
        btnConsultar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnConfirmar.setEnabled(false);
        btnEliminar.setEnabled(false);

        listar();
    }

    public void setEventBtnEditar(ActionEvent e) {

        txtNumeroRetiro.setEnabled(false);
        txtNombre.setEnabled(false);
        txtCurso.setEditable(false);
        cboMatricula.setEnabled(true);
        cboCodCurso.setEnabled(true);

        btnBuscar.setEnabled(false);
        btnRegistrar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnConfirmar.setEnabled(true);
        btnConsultar.setEnabled(false);
        btnEliminar.setEnabled(false);

    }

    public void setEventBtnConfirmar(ActionEvent e) {
        try {
            int codMatricula = leerCodigoMatricula();
            Matricula m = ArregloM.buscar(codMatricula);

            if (m != null && m.getEstado() == 2) {
                int nuevoCodCurso = leerNumeroCurso();
                m.setCodCurso(nuevoCodCurso);
                ArregloM.actualizarArchivo();

                listar();
                informacion("El curso ha sido cambiado correctamente.");
                btnRegistrar.setEnabled(true);
                btnConfirmar.setEnabled(false);
                btnConsultar.setEnabled(true);
                txtNombre.setEnabled(true);
                txtCurso.setEditable(true);
                cboMatricula.setEnabled(true);
                cboCodCurso.setEnabled(true);
            } else {
                error("La matrícula no está retirada o no existe.", txtNumeroRetiro);
            }
        } catch (Exception ex) {
            error("Error al editar el curso: " + ex.getMessage(), txtNumeroRetiro);
        }
    }

    public void setEventBtnEliminar(ActionEvent e) {
        try {
            int numRetiro = leerCodigoRetiro();
            Retiro r = ArregloR.buscar(numRetiro);

            if (r != null) {
                Matricula m = ArregloM.buscar(r.getNumMatricula());

                if (m != null && m.getEstado() == 2) {
                    int opcion = confirmar("¿Desea cancelar el retiro?");

                    if (opcion == JOptionPane.YES_OPTION) {
                        m.setEstado(1);
                        ArregloR.eliminar(r);

                        ArregloM.actualizarArchivo();
                        ArregloR.actualizarArchivo();

                        listar();
                        informacion("El retiro ha sido cancelado correctamente.");
                    }
                } else {
                    error("La matrícula no está retirada o no existe.", txtNumeroRetiro);
                }
            } else {
                error("El retiro no existe.", txtNumeroRetiro);
            }
        } catch (Exception ex) {
            error("Error al cancelar el retiro: " + ex.getMessage(), txtNumeroRetiro);
        }
    }

    public void setEventBtnBuscar(ActionEvent arg0) {

        txtNumeroRetiro.setEnabled(false);
        btnRegistrar.setEnabled(false);
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnConfirmar.setEnabled(false);

        try {
            int numeroRetiro = leerCodigoRetiro();

            Retiro retiro = ArregloR.buscar(numeroRetiro);

            if (retiro != null) {
                int codigoMatricula = retiro.getNumMatricula();

                Matricula matricula = ArregloM.buscar(codigoMatricula);

                if (matricula != null) {

                    Alumno alumno = ArregloA.buscar(matricula.getCodAlumno());
                    Curso curso = ArregloC.buscar(matricula.getCodCurso());

                    if (alumno != null && curso != null) {
                        txtNombre.setText(alumno.getNombre() + " " + alumno.getApellido());
                        txtCurso.setText(curso.getAsignatura());
                        cboMatricula.setSelectedItem(String.valueOf(matricula.getCodMatricula()));
                        cboCodCurso.setSelectedItem(String.valueOf(curso.getCodCurso()));

                        txtNumeroRetiro.setEnabled(true);
                        btnEliminar.setEnabled(true);
                    } else {
                        error("Datos incompletos para la matrícula ingresada.", txtNumeroRetiro);
                        btnConfirmar.setEnabled(true);
                        txtNumeroRetiro.setEnabled(true);
                    }
                } else {
                    error("El código de matrícula ingresado no existe.", txtNumeroRetiro);
                    btnConfirmar.setEnabled(true);
                    txtNumeroRetiro.setEnabled(true);
                }
            } else {
                error("El número de retiro ingresado no existe.", txtNumeroRetiro);
                btnConfirmar.setEnabled(true);
                txtNumeroRetiro.setEnabled(true);
            }
        } catch (NumberFormatException e) {
            error("Ingrese un número de retiro válido.", txtNumeroRetiro);
            btnConfirmar.setEnabled(true);
            txtNumeroRetiro.setEnabled(true);
        }

    }

    public void setEventCboMatricula(ActionEvent e) {
        cboM = Integer.parseInt((String) cboMatricula.getSelectedItem());
        txtNombre.setText("" + Mnombre());
    }

    public void setEventCboCodCurso(ActionEvent e) {
        cboC = Integer.parseInt((String) cboCodCurso.getSelectedItem());
        txtCurso.setText("" + Mcurso());
    }

    int leerCodigoRetiro() {
        return Integer.parseInt(txtNumeroRetiro.getText().trim());
    }

    int leerCodigoMatricula() {
        return cboM;
    }

    int leerNumeroCurso() {
        return cboC;
    }

    String obtenerDatosAlumno() {
        Matricula m = ArregloM.buscar(leerCodigoMatricula());
        Alumno a = ArregloA.buscar(m.getCodAlumno());
        return "Nombres :  " + a.getNombre() + "\n" + "Apellidos :  " + a.getApellido();
    }

    String obtenerDatosCurso() {
        Matricula m = ArregloM.buscar(leerCodigoMatricula());
        Curso c = ArregloC.buscar(m.getCodCurso());
        return "Asignatura :  " + c.getAsignatura();
    }

    String Mnombre() {
        Matricula m = ArregloM.buscar(leerCodigoMatricula());
        Alumno a = ArregloA.buscar(m.getCodAlumno());
        return a.getNombre() + "\n\n" + a.getApellido();
    }

    String Mcurso() {
        ArregloCurso ac = new ArregloCurso();
        Curso c = ac.buscar(cboC);
        return c.getAsignatura();
    }



    void listar() {
        modelo.setRowCount(0);
        for (int i = 0; i < ArregloR.tamano(); i++) {
            Retiro r = ArregloR.obtener(i);
            Matricula m = ArregloM.buscar(r.getNumMatricula());
            Alumno a = ArregloA.buscar(m.getCodAlumno());
            Curso c = ArregloC.buscar(m.getCodCurso());

            modelo.addRow(new Object[]{r.getNumRetiro(), r.getNumMatricula(), r.getFecha(), r.getHora(),
                    a.getNombre() + " " + a.getApellido(), c.getAsignatura()});
        }
    }

    void limpiar() {
        txtNumeroRetiro.setText("" + ArregloR.codigoCorrelativo());
        txtNombre.setText("");
        txtCurso.setText("");
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
        txtNumeroRetiro = (JTextField) componentsMap.get("txtNumeroRetiro");
        txtNombre = (JTextField) componentsMap.get("txtNombre");
        txtCurso = (JTextField) componentsMap.get("txtCurso" );
        btnRegistrar = (JButton) componentsMap.get("btnRegistrar" );
        btnConfirmar = (JButton) componentsMap.get("btnConfirmar" );
        btnConsultar = (JButton) componentsMap.get("btnConsultar" );
        btnEditar = (JButton) componentsMap.get("btnEditar" );
        btnEliminar = (JButton) componentsMap.get("btnEliminar" );
        btnBuscar = (JButton) componentsMap.get("btnBuscar" );
        cboMatricula = (JComboBox) componentsMap.get("cboMatricula" );
        cboCodCurso = (JComboBox) componentsMap.get("cboCodCurso" );
    }

    public void setDefaultTable(DefaultTableModel defaultTableModel){
        this.modelo = defaultTableModel;
    }
    public void setTableRetiro(JTable tblRetiro) {
        this.tblRetiro = tblRetiro;
    }
    public void setDialog(JDialog dialog){
        this.dialog = dialog;
    }
}

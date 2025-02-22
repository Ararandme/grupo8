package guis;

import javax.swing.*;

import arreglos.*;
import clases.*;
import eventos.RegistroRetiroEvent;
import libreria.Fecha;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class RegistroRetiro extends JDialog implements  ComponentEvents {

	private JLabel lblNmeroRetiro;
    private JTextField txtNumeroRetiro;
    private JLabel lblCdigoAlumno;
    private JLabel lblCdigoCurso;
    private JTextField txtNombre;
    private JTextField txtCurso;
    private JButton btnRegistrar;
    private JButton btnConfirmar;
    private JButton btnConsultar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JScrollPane scrollPane;
    private JComboBox<String> cboMatricula;
    private JComboBox<String> cboCodCurso;
    private JButton btnBuscar;
    private DefaultTableModel modelo;
    private JTable tblRetiro;


    ArregloRetiro ArregloR;
	private final RegistroRetiroEvent event;



    public RegistroRetiro(RegistroRetiroEvent event,ArregloRetiro ArregloR) {
		this.ArregloR = ArregloR;
		this.event = event;
        setSetting();
        sendComponents();
        sendingEvents();
        mostrarCodigosMatricula();
        mostrarCodigosCurso();
    }

    private void setSetting() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Registro | Retiro");
        setBounds(100, 100, 690, 450);
        getContentPane().setLayout(null);

        lblNmeroRetiro = new JLabel("Número Retiro");
        lblNmeroRetiro.setBounds(30, 30, 90, 25);
        getContentPane().add(lblNmeroRetiro);

        lblCdigoAlumno = new JLabel("N° Matrícula");
        lblCdigoAlumno.setBounds(30, 70, 90, 25);
        getContentPane().add(lblCdigoAlumno);

        lblCdigoCurso = new JLabel("Código Curso");
        lblCdigoCurso.setBounds(30, 110, 81, 25);
        getContentPane().add(lblCdigoCurso);

        txtNumeroRetiro = new JTextField();
        txtNumeroRetiro.setEnabled(false);
        txtNumeroRetiro.setBounds(130, 30, 90, 25);
        getContentPane().add(txtNumeroRetiro);
        txtNumeroRetiro.setColumns(10);
        txtNumeroRetiro.setText("" + ArregloR.codigoCorrelativo());

        txtNombre = new JTextField();
        txtNombre.setEditable(false);
        txtNombre.setBounds(245, 70, 300, 25);
        getContentPane().add(txtNombre);
        txtNombre.setColumns(10);

        txtCurso = new JTextField();
        txtCurso.setEditable(false);
        txtCurso.setBounds(245, 110, 300, 25);
        getContentPane().add(txtCurso);
        txtCurso.setColumns(10);

        cboMatricula = new JComboBox<String>();
        cboMatricula.setEnabled(false);
        cboMatricula.setEditable(true);
        cboMatricula.setBounds(130, 70, 90, 25);
        getContentPane().add(cboMatricula);

        cboCodCurso = new JComboBox<String>();
        cboCodCurso.setEditable(true);
        cboCodCurso.setEnabled(false);
        cboCodCurso.setBounds(130, 106, 90, 25);
        getContentPane().add(cboCodCurso);


        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setEnabled(false);
        btnBuscar.setBounds(245, 30, 110, 25);
        getContentPane().add(btnBuscar);

        btnRegistrar = new JButton("REGISTRAR");
        btnRegistrar.setBounds(45, 364, 110, 25);
        getContentPane().add(btnRegistrar);

        btnConfirmar = new JButton("CONFIRMAR");
        btnConfirmar.setEnabled(false);
        btnConfirmar.setBounds(405, 364, 110, 25);
        getContentPane().add(btnConfirmar);

        btnConsultar = new JButton("CONSULTAR");
        btnConsultar.setBounds(165, 364, 110, 25);
        getContentPane().add(btnConsultar);

        btnEditar = new JButton("EDITAR");
        btnEditar.setEnabled(false);
        btnEditar.setBounds(285, 364, 110, 25);
        getContentPane().add(btnEditar);

        btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.setBounds(525, 364, 110, 25);
        getContentPane().add(btnEliminar);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 166, 620, 175);
        getContentPane().add(scrollPane);

        tblRetiro = new JTable();
        tblRetiro.setFillsViewportHeight(true);
        scrollPane.setViewportView(tblRetiro);

        tblRetiro = new JTable();
        tblRetiro.setEnabled(false);
        tblRetiro.setFillsViewportHeight(true);
        scrollPane.setViewportView(tblRetiro);

        modelo = new DefaultTableModel();

        modelo = new DefaultTableModel(new Object[]{"NÚMERO", "MATRÍCULA", "FECHA", "HORA", "ALUMNO", "CURSO"}, 0);

        tblRetiro.setModel(modelo);
        setVisible(true);
    }

    void mostrarCodigosMatricula() {
        ArregloMatricula am = new ArregloMatricula();
        Matricula c;
        for (int i = 0; i < am.tamano(); i++) {
            c = am.obtener(i);
            cboMatricula.addItem("" + c.getCodMatricula());
        }
    }

    void mostrarCodigosCurso() {
        ArregloCurso ac = new ArregloCurso();
        Curso p;
        for (int i = 0; i < ac.tamano(); i++) {
            p = ac.obtener(i);
            cboCodCurso.addItem("" + p.getCodCurso());
        }
    }




    @Override
    public void sendingEvents() {
		btnRegistrar.addActionListener((e)->{event.setEventBtnRegistrar(e);});
		btnConsultar.addActionListener((e)->{event.setEventBtnConsultar(e);});
		btnEditar.addActionListener((e)->{event.setEventBtnEditar(e);});
		btnConfirmar.addActionListener((e)->{event.setEventBtnConfirmar(e);});
		btnEliminar.addActionListener((e)->{event.setEventBtnEliminar(e);});
		btnBuscar.addActionListener((e)->{event.setEventBtnBuscar(e);});
		cboMatricula.addActionListener((e)->{event.setEventCboMatricula(e);});
		cboCodCurso.addActionListener((e)->{event.setEventCboCodCurso(e);});
    }

    @Override
    public void sendComponents() {
        HashMap<String, JComponent> map = new HashMap<>();
        map.put("txtNumeroRetiro", txtNumeroRetiro);
        map.put("txtNombre", txtNombre);
        map.put("txtCurso", txtCurso);
        map.put("btnRegistrar", btnRegistrar);
        map.put("btnConfirmar", btnConfirmar);
        map.put("btnConsultar", btnConsultar);
        map.put("btnEditar", btnEditar);
        map.put("btnEliminar", btnEliminar);
        map.put("btnBuscar", btnBuscar);
        map.put("cboMatricula", cboMatricula);
        map.put("cboCodCurso", cboCodCurso);

        event.setComponent(map);
        event.setDefaultTable(modelo);
        event.setTableRetiro(tblRetiro);
    }
}
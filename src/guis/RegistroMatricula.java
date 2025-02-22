package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.io.IOException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

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

import arreglos.*;
import clases.*;
import eventos.RegistroMatriculaEvent;
import libreria.Fecha;
import libreria.Lib;

public class RegistroMatricula extends JDialog implements  ComponentEvents {



    private JLabel lblNmeroMatrcula;
    private JLabel lblCdigoAlumno;
    private JLabel lblCdigoCurso;
    private JTextField txtMatricula;
    private JTextField txtNomAlumno;
    private JTextField txtNomAsignatura;
    private JButton btnBuscar;
    private JButton btnRegistrar;
    private JButton btnConfirmar;
    private JButton btnConsultar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JScrollPane scrollPane;
    private JTable tblMatricula;
    private JComboBox<String> cboCodAlumno;
    private JComboBox<String> cboCodCurso;
    private DefaultTableModel modelo;
    private ArregloMatricula ArregloM;

	private RegistroMatriculaEvent event;


    public RegistroMatricula(RegistroMatriculaEvent registroMatriculaEvent,ArregloMatricula ArregloM) {
        this.ArregloM = ArregloM;
        this.event = registroMatriculaEvent;

        setSettings();
        sendingEvents();

    }

    public void setSettings(){
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Registro | Matrícula");
        setBounds(100, 100, 690, 450);
        getContentPane().setLayout(null);

        lblNmeroMatrcula = new JLabel("Nº Matrícula");
        lblNmeroMatrcula.setBounds(30, 30, 90, 25);
        getContentPane().add(lblNmeroMatrcula);

        lblCdigoAlumno = new JLabel("Código Alumno");
        lblCdigoAlumno.setBounds(30, 70, 90, 25);
        getContentPane().add(lblCdigoAlumno);

        lblCdigoCurso = new JLabel("Código Curso");
        lblCdigoCurso.setBounds(30, 110, 81, 25);
        getContentPane().add(lblCdigoCurso);

        txtMatricula = new JTextField();
        txtMatricula.setEnabled(false);
        txtMatricula.setBounds(130, 30, 90, 25);
        getContentPane().add(txtMatricula);
        txtMatricula.setText("" + ArregloM.codigoCorrelativo());

        txtNomAlumno = new JTextField();
        txtNomAlumno.setEditable(false);
        txtNomAlumno.setBounds(245, 70, 300, 25);
        getContentPane().add(txtNomAlumno);

        txtNomAsignatura = new JTextField();
        txtNomAsignatura.setEditable(false);
        txtNomAsignatura.setBounds(245, 110, 300, 25);
        getContentPane().add(txtNomAsignatura);

        cboCodAlumno = new JComboBox<>();

        cboCodAlumno.setBounds(130, 70, 90, 25);
        getContentPane().add(cboCodAlumno);
        mostrarCodigosAlumno();

        cboCodCurso = new JComboBox<>();
        cboCodCurso.setBounds(130, 110, 90, 25);
        getContentPane().add(cboCodCurso);
        mostrarCodigosCurso();

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

        tblMatricula = new JTable();
        tblMatricula.setEnabled(false);
        tblMatricula.setFillsViewportHeight(true);
        scrollPane.setViewportView(tblMatricula);

        modelo = new DefaultTableModel(new Object[]{"CÓDIGO", "FECHA", "HORA", "ALUMNO", "CURSO", "ESTADO"}, 0);

        tblMatricula.setModel(modelo);
        setVisible(true);
        sendComponents();
    }


    public void mostrarCodigosCurso() {
        ArregloCurso ArregloC = new ArregloCurso();
        Curso c;
        for (int i = 0; i < ArregloC.tamano(); i++) {
            c = ArregloC.obtener(i);
            cboCodCurso.addItem("" + c.getCodCurso());
        }
    }

    public void mostrarCodigosAlumno() {
        ArregloAlumno ArregloA  = new ArregloAlumno();
        Alumno a;
        for (int i = 0; i < ArregloA.tamano(); i++) {
            a = ArregloA.obtener(i);
            cboCodAlumno.addItem("" + a.getCodAlumno());
        }
    }


    @Override
    public void sendingEvents() {
        cboCodCurso.addActionListener((e)->{event.setCboCodCursoEvent();});
		cboCodAlumno.addActionListener((e)->{event.setCboCodAlumnoEvent();});
		btnBuscar.addActionListener((e)->{event.setBtnBuscarEvent();});
		btnEliminar.addActionListener((e)->{event.setBtnEliminarEvent();});
		btnEditar.addActionListener((e)->{event.setBtnEditarEvent();});
		btnConsultar.addActionListener((e)->{event.setBtnConsultarEvent();});
		btnRegistrar.addActionListener((e)->{event.setBtnRegistrarEvent();});
		btnConfirmar.addActionListener((e)->{event.setBtnConfirmarEvent();});
    }

    @Override
    public void sendComponents() {
        HashMap<String, JComponent> map = new HashMap<>();
        map.put("cboCodCurso",cboCodCurso);
        map.put("cboCodAlumno",cboCodAlumno);
        map.put("btnBuscar",btnBuscar);
        map.put("btnEliminar",btnEliminar);
        map.put("btnEditar",btnEditar);
        map.put("btnConsultar",btnConsultar);
        map.put("btnRegistrar",btnRegistrar);
        map.put("btnConfirmar",btnConfirmar);
        map.put("txtMatricula",txtMatricula);
        map.put("txtNomAlumno",txtNomAlumno);
        map.put("txtNomAsignatura",txtNomAsignatura);


        event.setComboBox(cboCodCurso,cboCodAlumno);

        event.setComponent(map);
        event.setDefaultTable(this.modelo);
    }

}

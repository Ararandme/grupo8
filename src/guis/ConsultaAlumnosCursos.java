package guis;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

import clases.*;
import eventos.ConsultaAlumnosCursoEvent;
import libreria.Lib;
import arreglos.*;

public class ConsultaAlumnosCursos extends JDialog implements  ComponentEvents{

	private JLabel lblCdigoAlumno;
	private JTextField txtCodAlumno;
	private JButton btnBuscar;
	private JTable tbAlumnos;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JTextField txtCodCurso;
	private JTable tbCursos;

	private DefaultTableModel alumnosTableModel;
	private DefaultTableModel cursosTableModel;

	ConsultaAlumnosCursoEvent eventClass;

	public ConsultaAlumnosCursos(ConsultaAlumnosCursoEvent event) {
		this.eventClass = event;
		setSetting();
		sendComponents();
		sendingEvents();
	}



	private void setSetting(){

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Consulta | Alumnos y Cursos");
		setBounds(100, 100, 750, 340);
		getContentPane().setLayout(null);

		lblCdigoAlumno = new JLabel("Código Alumno:");
		lblCdigoAlumno.setBounds(30, 30, 100, 25);
		getContentPane().add(lblCdigoAlumno);

		txtCodAlumno = new JTextField();
		txtCodAlumno.setBounds(140, 30, 120, 25);
		getContentPane().add(txtCodAlumno);
		txtCodAlumno.setColumns(10);


		lblNewLabel = new JLabel("Código Curso:");
		lblNewLabel.setBounds(298, 30, 107, 25);
		getContentPane().add(lblNewLabel);

		txtCodCurso = new JTextField();
		txtCodCurso.setBounds(402, 30, 120, 25);
		getContentPane().add(txtCodCurso);
		txtCodCurso.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(566, 30, 140, 25);
		getContentPane().add(btnBuscar);

		alumnosTableModel  = new DefaultTableModel(new Object[] { "Código Alumno", "Nombres", "Apellidos", "DNI", "Edad", "Celular", "Estado" }, 0);
		tbAlumnos = new JTable(alumnosTableModel);
		tbAlumnos.setEnabled(false);
		scrollPane = new JScrollPane(tbAlumnos);
		scrollPane.setBounds(30, 81, 676, 82);
		getContentPane().add(scrollPane);

		cursosTableModel = new DefaultTableModel(
				new Object[] { "Curso", "Asignatura", "Ciclo", "Cantidad de creditos", "Cantidad de horas" }, 0);
		tbCursos = new JTable(cursosTableModel);
		tbCursos.setEnabled(false);
		scrollPane = new JScrollPane(tbCursos);
		scrollPane.setBounds(30, 188, 676, 82);
		getContentPane().add(scrollPane);


	}


	@Override
	public void sendingEvents() {

		btnBuscar.addActionListener((e)->{eventClass.setBtnBuscar(e);});

	}

	@Override
	public void sendComponents() {
		eventClass.setDialog(this);
		HashMap<String, JComponent> map = new HashMap<>();
		map.put("txtCodAlumno", txtCodAlumno);
		map.put("txtCodCurso", txtCodCurso);
		map.put("btnBuscar", btnBuscar);
		eventClass.setComponent(map);
		eventClass.setTable(alumnosTableModel,cursosTableModel);
	}


}

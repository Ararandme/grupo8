package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import arreglos.ArregloUsuarios;
import eventos.ConsultaMatriculasRetirosEvent;

import java.util.HashMap;

public class ConsultaMatriculasRetiros extends JDialog implements ComponentEvents {

	private JLabel lblCdigoAlumno;
	private JTextField txtCodMatricula;
	private JButton btnBuscar;
	private JTable tbMatricula;
	private JTable tbRetiro;
	private DefaultTableModel matriculaTableModel;
	private DefaultTableModel retiroTableModel;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JTextField txtCodRetiro;
	private ConsultaMatriculasRetirosEvent event;






	public ConsultaMatriculasRetiros(ConsultaMatriculasRetirosEvent event) {
		this.event = event;
		setSetting();
		sendComponents();
		sendingEvents();

	}

	private void setSetting(){

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Consulta | Matriculas y Retiros");
		setBounds(100, 100, 1020, 340);
		getContentPane().setLayout(null);

		lblCdigoAlumno = new JLabel("N° Matricula:");
		lblCdigoAlumno.setBounds(30, 30, 100, 25);
		getContentPane().add(lblCdigoAlumno);

		txtCodMatricula = new JTextField();
		txtCodMatricula.setBounds(140, 30, 120, 25);
		getContentPane().add(txtCodMatricula);
		txtCodMatricula.setColumns(10);

		lblNewLabel = new JLabel("N° Retiro:");
		lblNewLabel.setBounds(510, 30, 107, 25);
		getContentPane().add(lblNewLabel);

		txtCodRetiro = new JTextField();
		txtCodRetiro.setBounds(614, 30, 120, 25);
		getContentPane().add(txtCodRetiro);
		txtCodRetiro.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(778, 30, 140, 25);
		getContentPane().add(btnBuscar);

		matriculaTableModel = new DefaultTableModel(new Object[] { "Matricula", "Estado", "Alumno", "Nombres",
				"Apellidos", "DNI", "Edad", "Celular", "Curso", "Asignatura", "Ciclo", "creditos", "horas" }, 0);
		tbMatricula = new JTable(matriculaTableModel);
		tbMatricula.setEnabled(false);
		scrollPane = new JScrollPane(tbMatricula);
		scrollPane.setBounds(30, 81, 888, 82);
		getContentPane().add(scrollPane);

		retiroTableModel = new DefaultTableModel(new Object[] { "N° Retiro", "N° Matricula", "Alumno", "Nombres",
				"Apellidos", "Edad", "DNI", "Edad", "Celular", "Curso", "Asignatura", "Ciclo", "creditos", "horas" },
				0);
		tbRetiro = new JTable(retiroTableModel);
		tbRetiro.setEnabled(false);
		scrollPane = new JScrollPane(tbRetiro);
		scrollPane.setBounds(30, 188, 888, 82);
		getContentPane().add(scrollPane);
		setVisible(true);
	}






	@Override
	public void sendingEvents() {
		btnBuscar.addActionListener((e)->{event.setBtnEvent(e);});
	}

	@Override
	public void sendComponents() {
		HashMap<String,JComponent> map = new HashMap<>();
		map.put("txtCodMatricula",txtCodMatricula);
		map.put("txtCodRetiro",txtCodRetiro);
		map.put("btnBuscar",btnBuscar);
		event.setComponent(map);
		event.setDialog(this);
		event.setDefaultTable(matriculaTableModel, retiroTableModel);
	}
}

package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.HashMap;

import eventos.ReporteAlumnnosPendienteEvent;

public class ReporteAlumnosPendientes extends JDialog implements  ComponentEvents {

	private JButton btnMostrar;
	private JButton btnGenerarExcel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;

	private final ReporteAlumnnosPendienteEvent event;



	public ReporteAlumnosPendientes(ReporteAlumnnosPendienteEvent event) {
		this.event = event;
		setSettings();
		sendComponents();
		sendingEvents();
	}

	private void setSettings(){
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Reporte | Alumnos con Matrículas Pendientes");
		setBounds(100, 100, 750, 320);
		getContentPane().setLayout(null);
		btnMostrar = new JButton("MOSTRAR");
		btnMostrar.setBounds(224, 232, 100, 30);
		this.setBackground(new Color(105, 95, 188));

		getContentPane().add(btnMostrar);

		btnGenerarExcel = new JButton("GENERAR XML");
		btnGenerarExcel.setBounds(365, 229, 150, 30);
		btnGenerarExcel.setEnabled(false);
		getContentPane().add(btnGenerarExcel);

		tableModel = new DefaultTableModel(
				new Object[] { "Código del Alumno", "Nombres", "Apellidos", "DNI", "Edad", "Celular" }, 0);

		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(31, 22, 675, 183);
		getContentPane().add(scrollPane);
		setVisible(true);
	}



	@Override
	public void sendingEvents() {
		btnMostrar.addActionListener((e) -> {event.actionPerformedBtnMostrar(e);});
		btnGenerarExcel.addActionListener((e) -> {event.actionPerformedBtnGenerarExcel(e);});

	}

	@Override
	public void sendComponents() {
		HashMap<String,JComponent> map = new HashMap<>();
		map.put("btnMostrar",btnMostrar);
		map.put("btnGenerarExcel",btnGenerarExcel);
		map.put("scrollPane",scrollPane);
		event.setComponent(map);

		event.setDialog(this);
		event.setDefaultTableModel(tableModel);
		event.setTableModel(table);

	}
}

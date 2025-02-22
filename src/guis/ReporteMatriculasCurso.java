package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import eventos.ReporteAlummnosVigentesEvent;
import eventos.ReporteMatriculasCursoEvent;

import java.util.HashMap;

public class ReporteMatriculasCurso extends JDialog implements  ComponentEvents  {

	private JButton btnGenerar;
	private JButton btnGenerarExcel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private final ReporteMatriculasCursoEvent event;



	public ReporteMatriculasCurso(ReporteMatriculasCursoEvent event) {
		this.event = event;
		setSetting();
		sendComponents();
		sendingEvents();
	}

	public void setSetting(){
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Reporte | Alumnos Matriculados por Curso");
		setBounds(100, 100, 750, 320);
		getContentPane().setLayout(null);

		btnGenerar = new JButton("MOSTRAR");
		btnGenerar.setBounds(224, 232, 100, 30);
		getContentPane().add(btnGenerar);

		btnGenerarExcel = new JButton("GENERAR XML");
		btnGenerarExcel.setBounds(365, 229, 150, 30);
		btnGenerarExcel.setEnabled(false);
		getContentPane().add(btnGenerarExcel);

		tableModel = new DefaultTableModel(new Object[] { "N° MATRICULA", "NOMBRE", "APELLIDO", "CURSO" }, 0);
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(31, 22, 675, 183);
		getContentPane().add(scrollPane);
		setVisible(true);
	}

	
	
	@Override
	public void sendingEvents() {
		btnGenerar.addActionListener((e) -> {event.actionPerformedBtnMostrar(e);});
		btnGenerarExcel.addActionListener((e) -> {event.actionPerformedBtnGenerarExcel(e);});
	}

	@Override
	public void sendComponents() {
		HashMap<String, JComponent> map = new HashMap<>();
		map.put("btnGenerar",btnGenerar);
		map.put("btnGenerarExcel",btnGenerarExcel);
		map.put("scrollPane",scrollPane);

		event.setComponent(map);
		event.setTableModel(table);
		event.setDefaultTableModel(tableModel);
	}
	
}

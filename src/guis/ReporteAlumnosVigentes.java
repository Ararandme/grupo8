package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import eventos.ReporteAlummnosVigentesEvent;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import arreglos.ArregloAlumno;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import clases.Alumno;

public class ReporteAlumnosVigentes extends JDialog implements ComponentEvents {

	private JButton btnGenerar;
	private JButton btnGenerarExcel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private final ReporteAlummnosVigentesEvent event;



	public ReporteAlumnosVigentes(ReporteAlummnosVigentesEvent event) {
		this.event = event;
		setSetting();
		sendComponents();
		sendingEvents();
		System.out.println("ArregloA");
	}

	public void setSetting() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setTitle("Reporte | Alumnos con Matrícula Vigente");
		setBounds(100, 100, 750, 320);
		getContentPane().setLayout(null);

		btnGenerar = new JButton("MOSTRAR");
		btnGenerar.setBounds(224, 232, 100, 30);
		getContentPane().add(btnGenerar);

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

		btnGenerar.addActionListener((e) -> {
			event.actionPerformedBtnMostrar(e);});
		btnGenerarExcel.addActionListener((e) -> {event.actionPerformedBtnGenerarExcel(e);});
	}

	@Override
	public void sendComponents() {
		HashMap<String, JComponent> map = new HashMap<>();
		map.put("btnGenerar",btnGenerar);
		map.put("btnGenerarExcel",btnGenerarExcel);
		map.put("scrollPane",scrollPane);

		event.setComponent(map);
		event.setTableModel(tableModel);
		event.setTable(table);
	}
}

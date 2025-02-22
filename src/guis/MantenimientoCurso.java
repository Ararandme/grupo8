package guis;

import javax.swing.*;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import arreglos.ArregloCurso;
import arreglos.ArregloMatricula;
import eventos.MantenimientoCursoEvent;

public class MantenimientoCurso extends JDialog implements ComponentEvents {

	private JLabel lblCodigo;
	private JLabel lblHoras;
	private JTextField txtCodigo;
	private JTextField txtHoras;
	private JLabel lblCiclo;
	private JLabel lblCreditos;
	private JComboBox<String> cboCiclo;
	private JTextField txtCreditos;
	private JLabel lblAsignatura;
	private JTextField txtAsignatura;
	private JButton btnEliminar;
	private JButton btnConfirmar;
	private JButton btnRegistrar;
	private JButton btnConsultar;
	private JButton btnEditar;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private JTable tblCurso;
	private DefaultTableModel modelo;

	private ArregloCurso ArregloC;
	private ArregloMatricula ArregloM;

	private MantenimientoCursoEvent event;

	public MantenimientoCurso(MantenimientoCursoEvent event,
							  ArregloCurso arregloCurso,
							  ArregloMatricula arregloM) {
	this.ArregloC = arregloCurso;
	this.ArregloM = arregloM;
	this.event = event;
		setSetting();
		sendComponents();
		sendingEvents();
	}

	private void setSetting(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Mantenimiento | Curso");
		setBounds(100, 100, 670, 420);
		getContentPane().setLayout(null);

		lblAsignatura = new JLabel("Asignatura");
		lblAsignatura.setBounds(20, 56, 80, 25);
		getContentPane().add(lblAsignatura);

		txtAsignatura = new JTextField();
		txtAsignatura.setBounds(110, 60, 268, 25);
		getContentPane().add(txtAsignatura);
		txtAsignatura.setColumns(10);

		lblCodigo = new JLabel("Código");
		lblCodigo.setBounds(20, 20, 60, 25);
		getContentPane().add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(110, 20, 80, 25);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);

		lblHoras = new JLabel("Horas");
		lblHoras.setBounds(20, 100, 60, 25);
		getContentPane().add(lblHoras);

		txtHoras = new JTextField();
		txtHoras.setBounds(110, 100, 80, 25);
		getContentPane().add(txtHoras);
		txtHoras.setColumns(10);

		lblCiclo = new JLabel("Ciclo");
		lblCiclo.setBounds(220, 20, 50, 25);
		getContentPane().add(lblCiclo);

		cboCiclo = new JComboBox<String>();
		cboCiclo.setEditable(false);
		cboCiclo.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Seleccionar","Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto" }));
		cboCiclo.setBounds(268, 20, 110, 25);
		getContentPane().add(cboCiclo);

		lblCreditos = new JLabel("Créditos");
		lblCreditos.setBounds(20, 140, 60, 25);
		getContentPane().add(lblCreditos);

		txtCreditos = new JTextField();
		txtCreditos.setBounds(110, 140, 80, 25);
		getContentPane().add(txtCreditos);
		txtCreditos.setColumns(10);

		btnRegistrar = new JButton("REGISTRAR");
		btnRegistrar.setBounds(530, 20, 110, 25);
		getContentPane().add(btnRegistrar);

		btnBuscar = new JButton("BUSCAR");
		btnBuscar.setEnabled(false);
		btnBuscar.setBounds(410, 20, 110, 25);
		getContentPane().add(btnBuscar);

		btnConsultar = new JButton("CONSULTAR");
		btnConsultar.setBounds(530, 60, 110, 25);
		getContentPane().add(btnConsultar);

		btnEditar = new JButton("EDITAR");
		btnEditar.setEnabled(false);
		btnEditar.setBounds(410, 100, 110, 25);
		getContentPane().add(btnEditar);

		btnConfirmar = new JButton("CONFIRMAR");
		btnConfirmar.setEnabled(false);
		btnConfirmar.setBounds(530, 100, 110, 25);
		getContentPane().add(btnConfirmar);

		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(530, 140, 110, 25);
		getContentPane().add(btnEliminar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 183, 620, 187);
		getContentPane().add(scrollPane);

		tblCurso = new JTable();
		tblCurso.setEnabled(false);
		tblCurso.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblCurso);

		modelo = new DefaultTableModel(new Object[] { "CODIGO", "ASIGNATURA", "CICLO", "HORAS", "CREDITOS"  }, 0);

		tblCurso.setModel(modelo);
		setVisible(true);
	}




	@Override
	public void sendingEvents() {
		btnConfirmar.addActionListener((e)->{event.setEventBtnConfirmar(e);});
		btnConsultar.addActionListener((e)->{event.setEventBtnConsultar(e);});
		btnEditar.addActionListener((e)->{event.setEventBtnEditar(e);});
		btnEliminar.addActionListener((e)->{event.setEventBtnEliminar(e);});
		btnBuscar.addActionListener((e)->{event.setEventBtnBuscar(e);});
		btnRegistrar.addActionListener((e)->{event.setEventBtnRegistrar(e);});

	}

	@Override
	public void sendComponents() {
		HashMap<String, JComponent> map = new HashMap<>();
		map.put("txtCodigo",txtCodigo);
		map.put("txtHoras",txtHoras);
		map.put("txtCreditos",txtCreditos);
		map.put("btnBuscar",btnBuscar);
		map.put("btnRegistrar",btnRegistrar);
		map.put("btnConfirmar",btnConfirmar);
		map.put("btnEliminar",btnEliminar);
		map.put("btnEditar",btnEditar);
		map.put("btnConsultar",btnConsultar);
		map.put("txtAsignatura",txtAsignatura);
		map.put("cboCiclo",cboCiclo);
		event.setDefaulTable(modelo);
		event.setComponent(map);
	}
}

package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import arreglos.ArregloAlumno;
import eventos.MantenimientoAlumnoEvent;

import java.util.HashMap;

public class MantenimientoAlumno extends JDialog implements ComponentEvents {


	private JLabel lblCdigo;
	private JLabel lblDni;
	private JLabel lblNombres;
	private JLabel lblApellidos;
	private JTextField txtCodigo;
	private JTextField txtDni;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JLabel lblEstado;
	private JComboBox<String> cboEstado;
	private JLabel lblEdad;
	private JTextField txtEdad;
	private JLabel lblCelular;
	private JTextField txtCelular;
	private JButton btnBuscar;
	private JButton btnRegistrar;
	private JButton btnConsultar;
	private JButton btnEditar;
	private JButton btnConfirmar;
	private JButton btnEliminar;
	private JScrollPane scrollPane;
	private JTable tblAlumno;
	private DefaultTableModel modelo;

	private final ArregloAlumno ArregloA;
    private final MantenimientoAlumnoEvent event;


	public MantenimientoAlumno(MantenimientoAlumnoEvent event, ArregloAlumno arregloAlumno) {
		this.event = event;
		this.ArregloA = arregloAlumno;
        setSetting();


	}

	private void setSetting(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Mantenimiento | Alumno");
		setBounds(100, 100, 815, 420);
		getContentPane().setLayout(null);

		lblCdigo = new JLabel("Codigo :");
		lblCdigo.setBounds(20, 20, 60, 25);
		getContentPane().add(lblCdigo);

		txtCodigo = new JTextField();
		txtCodigo.setEnabled(false);
		txtCodigo.setBounds(90, 20, 100, 25);
		txtCodigo.setText("" + ArregloA.numeroCorrelativo());
		getContentPane().add(txtCodigo);

		lblDni = new JLabel("DNI :");
		lblDni.setBounds(20, 60, 60, 25);
		getContentPane().add(lblDni);

		txtDni = new JTextField();
		txtDni.setBounds(90, 60, 100, 25);
		getContentPane().add(txtDni);

		lblEdad = new JLabel("Edad :");
		lblEdad.setBounds(240, 60, 40, 25);
		getContentPane().add(lblEdad);

		txtEdad = new JTextField();
		txtEdad.setBounds(290, 60, 50, 25);
		getContentPane().add(txtEdad);

		lblNombres = new JLabel("Nombres :");
		lblNombres.setBounds(20, 100, 60, 25);
		getContentPane().add(lblNombres);

		txtNombres = new JTextField();
		txtNombres.setBounds(90, 100, 250, 25);
		getContentPane().add(txtNombres);

		lblApellidos = new JLabel("Apellidos :");
		lblApellidos.setBounds(20, 140, 60, 25);
		getContentPane().add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setBounds(90, 140, 250, 25);
		getContentPane().add(txtApellidos);

		lblEstado = new JLabel("Estado :");
		lblEstado.setBounds(361, 20, 60, 25);
		getContentPane().add(lblEstado);

		cboEstado = new JComboBox<String>();
		cboEstado.setEnabled(false);
		cboEstado.setModel(new DefaultComboBoxModel<String>(new String[] { "Registrado", "Matriculado", "Retirado" }));
		cboEstado.setBounds(421, 20, 120, 25);
		getContentPane().add(cboEstado);

		lblCelular = new JLabel("Celular :");
		lblCelular.setBounds(361, 60, 60, 25);
		getContentPane().add(lblCelular);

		txtCelular = new JTextField();
		txtCelular.setBounds(421, 60, 120, 25);
		getContentPane().add(txtCelular);

		btnBuscar = new JButton("BUSCAR");
		btnBuscar.setEnabled(false);
		btnBuscar.setBounds(240, 20, 100, 25);
		getContentPane().add(btnBuscar);

		btnRegistrar = new JButton("REGISTRAR");
		btnRegistrar.setBounds(672, 20, 110, 25);
		getContentPane().add(btnRegistrar);

		btnConsultar = new JButton("CONSULTAR");
		btnConsultar.setBounds(672, 60, 110, 25);
		getContentPane().add(btnConsultar);

		btnEditar = new JButton("EDITAR");
		btnEditar.setEnabled(false);
		btnEditar.setBounds(552, 100, 110, 25);
		getContentPane().add(btnEditar);

		btnConfirmar = new JButton("CONFIRMAR");
		btnConfirmar.setEnabled(false);
		btnConfirmar.setBounds(672, 100, 110, 25);
		getContentPane().add(btnConfirmar);

		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(672, 140, 110, 25);
		getContentPane().add(btnEliminar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 185, 762, 180);
		getContentPane().add(scrollPane);

		tblAlumno = new JTable();
		tblAlumno.setEnabled(false);
		tblAlumno.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblAlumno);

		modelo = new DefaultTableModel(
				new Object[] { "CODIGO", "NOMBRES", "APELLIDOS", "DNI", "EDAD", "CELULAR", "ESTADO" }, 0);
		tblAlumno.setModel(modelo);

		setVisible(true);
		sendComponents();
		sendingEvents();
	}


	@Override
	public void sendingEvents() {
		btnBuscar.addActionListener((e)->{event.setEventBtnBuscar(e);});
		btnRegistrar.addActionListener((e)->{event.setEventBtnRegistrar(e);});
		btnConfirmar.addActionListener((e)->{event.setEventBtnConfirmar(e);});
		btnEliminar.addActionListener((e)->{event.setEventBtnEliminar(e);});
		btnEditar.addActionListener((e)->{event.setEventBtnEditar(e);});
		btnConsultar.addActionListener((e)->{event.setEventBtnBtnConsultar(e);});
	}

	@Override
	public void sendComponents() {
		HashMap<String, JComponent> map = new HashMap<>();
		map.put("txtNombres",txtNombres);
		map.put("txtApellidos",txtApellidos);
		map.put("txtCelular",txtCelular);
		map.put("txtCodigo",txtCodigo);
		map.put("txtDni",txtDni);
		map.put("txtEdad",txtEdad);
		map.put("cboEstado",cboEstado);
		map.put("btnBuscar",btnBuscar);
		map.put("btnRegistrar",btnRegistrar);
		map.put("btnConfirmar",btnConfirmar);
		map.put("btnEliminar",btnEliminar);
		map.put("btnEditar",btnEditar);
		map.put("btnConsultar",btnConsultar);
		event.setDefaulTable(modelo);
		event.setComponent(map);
		}



}

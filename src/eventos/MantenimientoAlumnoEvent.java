package eventos;

import arreglos.ArregloAlumno;
import clases.Alumno;
import libreria.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class MantenimientoAlumnoEvent implements ComponentsSetters {
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtCelular;
    private JTextField txtCodigo;
    private JTextField txtDni;
    private JTextField txtEdad ;
    private JComboBox<String> cboEstado;
    private JButton btnRegistrar;
    private JButton btnConfirmar;
    private JButton btnEliminar;
    private JButton btnEditar;
    private JButton btnConsultar;
    private JButton btnBuscar;

    private DefaultTableModel modelo;

    private JDialog dialog;
    private ArregloAlumno ArregloA;

    public MantenimientoAlumnoEvent(ArregloAlumno ArregloA){
        this.ArregloA = ArregloA;
    }




    public void setEventBtnRegistrar(ActionEvent e) {
        try {
            // Se leen y almacenan los datos ingresados por el usuario
            String dni = leerDNI();
            String nombre = leerNombres();
            String apellido = leerApellidos();
            int numeroAlumno = leerNumeroAlumno();
            int edad = leerEdad();
            int celular = leerCelular();
            int estado = leerEstado();

            // Se verifica si el DNI ya está registrado en el arreglo de alumnos
            Alumno alumnoExistente = ArregloA.buscarDNI(dni);
            if (alumnoExistente != null) {
                // Muestra un mensaje de error si el DNI ya existe y detiene el proceso de registro
                error("El DNI ingresado ya está registrado.", txtDni);
                return;
            } else {
                // Crea un nuevo objeto Alumno con los datos ingresados
                Alumno alumno = new Alumno(numeroAlumno, nombre, apellido, dni, edad, celular, estado);

                // Agrega el nuevo alumno al arreglo
                ArregloA.adicionar(alumno);

                // Actualiza la lista de alumnos en la interfaz
                listar();

                // Limpia los campos del formulario
                limpiar();

                // Muestra un mensaje de confirmación de registro exitoso
                informacion("Alumno registrado correctamente.");
            }
        } catch (Exception ex) {
            // Captura cualquier excepción y devuelve el foco al campo de DNI
            txtDni.requestFocus();
        }
    }

    public void setEventBtnBuscar(ActionEvent e) {

        // Habilita los botones de edición y eliminación, y deshabilita el de confirmación
        btnEditar.setEnabled(true);
        btnConfirmar.setEnabled(false);
        btnEliminar.setEnabled(true);

        try {
            // Obtiene el código ingresado por el usuario
            int codigo = leerNumeroAlumno();

            // Busca el alumno en el arreglo utilizando el código
            Alumno a = ArregloA.buscar(codigo);

            if (a != null) {
                // Si el alumno existe, llena los campos del formulario con sus datos
                txtNombres.setText(a.getNombre());
                txtApellidos.setText(a.getApellido());
                txtDni.setText(a.getDni());
                txtEdad.setText(String.valueOf(a.getEdad()));
                txtCelular.setText(String.valueOf(a.getCelular()));
                cboEstado.setSelectedIndex(a.getEstado());
            } else {
                // Si no se encuentra el alumno, muestra un mensaje de error
                error("Alumno no encontrado.", txtCodigo);
            }
        } catch (Exception ex) {
            // Captura cualquier error y muestra un mensaje indicando que el código no es válido
            error("Ingrese un código válido.", txtCodigo);
        }
    }

    void listar() {
        // Reinicia el modelo de la tabla eliminando todas las filas existentes
        modelo.setRowCount(0);

        // Recorre el arreglo de alumnos y agrega cada uno a la tabla
        for (int i = 0; i < ArregloA.tamano(); i++) {
            // Obtiene el alumno en la posición 'i' del arreglo
            Alumno a = ArregloA.obtener(i);

            // Agrega una nueva fila a la tabla con los datos del alumno
            modelo.addRow(new Object[] {
                    a.getCodAlumno(),   // Código del alumno
                    a.getNombre(),      // Nombre del alumno
                    a.getApellido(),    // Apellido del alumno
                    a.getDni(),         // DNI del alumno
                    a.getEdad(),        // Edad del alumno
                    a.getCelular(),     // Número de celular del alumno
                    TextoEstado(a.getEstado()) // Estado del alumno convertido a texto
            });
        }
    }


    void limpiar() {
        txtCodigo.setText("" + ArregloA.numeroCorrelativo());
        txtDni.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtCelular.setText("");
        cboEstado.setSelectedIndex(0);
    }

    // Método para leer y validar el nombre ingresado en el campo de texto
    String leerNombres() {
        return Validador.validarNombres(txtNombres.getText(), txtNombres);
    }

    // Método para leer y validar el apellido ingresado en el campo de texto
    String leerApellidos() {
        return Validador.validarApellidos(txtApellidos.getText(), txtApellidos);
    }

    // Método para leer y validar el DNI ingresado en el campo de texto
    String leerDNI() {
        return Validador.validarDNI(txtDni.getText(), txtDni);
    }

    // Método para obtener y convertir el número de alumno ingresado a un entero
    int leerNumeroAlumno() {
        return Integer.parseInt(txtCodigo.getText().trim());
    }

    // Método para leer y validar la edad ingresada en el campo de texto
    int leerEdad() {
        return Validador.validarEdad(txtEdad.getText(), txtApellidos);
    }

    // Método para leer y validar el número de celular ingresado en el campo de texto
    int leerCelular() {
        return Validador.validarCelular(txtCelular.getText(), txtApellidos);
    }

    // Método para obtener el índice del estado seleccionado en el combo box
    int leerEstado() {
        return cboEstado.getSelectedIndex();
    }


    String TextoEstado(int estado) {
        return cboEstado.getItemAt(estado);
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

    public void setEventBtnBtnConsultar(ActionEvent e) {
        txtCodigo.setEnabled(true);
        txtDni.setEnabled(false);
        txtNombres.setEnabled(false);
        txtApellidos.setEnabled(false);
        txtEdad.setEnabled(false);
        txtCelular.setEnabled(false);
        cboEstado.setEnabled(false);

        txtCodigo.setText("");
        txtCodigo.requestFocus();

        btnBuscar.setEnabled(true);
        btnRegistrar.setEnabled(false);
        btnConsultar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnConfirmar.setEnabled(false);
        btnEliminar.setEnabled(false);

        listar();
    }

    public void setEventBtnEditar(ActionEvent e) {
        txtCodigo.setEnabled(false);
        txtDni.setEnabled(false);
        txtNombres.setEnabled(true);
        txtApellidos.setEnabled(true);
        txtEdad.setEnabled(true);
        txtCelular.setEnabled(true);

        btnBuscar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnConfirmar.setEnabled(true);
    }

    public void setEventBtnConfirmar(ActionEvent e) {

        // Habilita la edición del campo DNI y coloca el foco en él
        txtDni.setEnabled(true);
        txtDni.requestFocus();

        // Deshabilita los botones de registro y consulta mientras se confirma la edición
        btnRegistrar.setEnabled(false);
        btnConsultar.setEnabled(false);

        try {
            // Obtiene el código del alumno ingresado por el usuario
            int codigo = leerNumeroAlumno();

            // Busca el alumno en el arreglo utilizando el código
            Alumno a = ArregloA.buscar(codigo);

            if (a != null) {
                // Si el alumno existe, actualiza sus datos con la información ingresada
                a.setNombre(leerNombres());
                a.setApellido(leerApellidos());
                a.setDni(leerDNI());
                a.setEdad(leerEdad());
                a.setCelular(leerCelular());
                a.setEstado(leerEstado());

                // Guarda los cambios en el archivo de datos
                ArregloA.actualizarArchivo();

                // Actualiza la lista de alumnos en la interfaz
                listar();

                // Limpia los campos del formulario
                limpiar();

                // Muestra un mensaje de confirmación de actualización exitosa
                informacion("Datos actualizados correctamente.");

                // Restablece el estado de los botones
                btnRegistrar.setEnabled(true);
                btnConsultar.setEnabled(true);
                btnConfirmar.setEnabled(false);
            } else {
                // Si no se encuentra el alumno, muestra un mensaje de error y permite reintentar la confirmación
                error("Alumno no encontrado.", txtCodigo);
                btnConfirmar.setEnabled(true);
            }
        } catch (Exception ex) {
            // Si ocurre un error, mantiene habilitado el botón de confirmación para reintentar
            btnConfirmar.setEnabled(true);
        }
    }

    public void setEventBtnEliminar(ActionEvent e) {

        // Coloca el foco en el campo DNI antes de iniciar el proceso de eliminación
        txtDni.requestFocus();

        try {
            // Obtiene el código del alumno ingresado por el usuario
            int codigo = leerNumeroAlumno();

            // Busca el alumno en el arreglo utilizando el código
            Alumno a = ArregloA.buscar(codigo);

            if (a != null) {
                // Verifica si el alumno tiene el estado "Registrado" (asumiendo que estado 0 es registrado)
                if (a.getEstado() == 0) {
                    // Solicita confirmación al usuario antes de eliminar el registro
                    int confirm = confirmar("¿Está seguro de eliminar este registro?");
                    if (confirm == 0) { // 0 indica que el usuario confirmó la eliminación
                        // Elimina el alumno del arreglo
                        ArregloA.eliminar(a);

                        // Actualiza la lista de alumnos en la interfaz
                        listar();

                        // Limpia los campos del formulario
                        limpiar();

                        // Muestra un mensaje de confirmación de eliminación exitosa
                        informacion("Alumno eliminado correctamente.");

                        // Restablece el estado de los campos y botones
                        txtCodigo.setEnabled(false);
                        txtDni.setEnabled(true);
                        txtNombres.setEnabled(true);
                        txtApellidos.setEnabled(true);
                        txtEdad.setEnabled(true);
                        txtCelular.setEnabled(true);
                        btnRegistrar.setEnabled(true);

                        // Limpia nuevamente los campos para evitar inconsistencias
                        limpiar();
                    }
                } else {
                    // Muestra un mensaje indicando que solo se pueden eliminar alumnos con estado "Registrado"
                    informacion("Solo se pueden eliminar alumnos con estado 'Registrado'.");
                }
            } else {
                // Si no se encuentra el alumno, muestra un mensaje de error
                error("Alumno no encontrado.", txtCodigo);
            }
        } catch (Exception ex) {
            // Captura cualquier error y muestra un mensaje con la descripción del problema
            error("Error al eliminar: " + ex.getMessage(), txtCodigo);
        }
    }


    @Override
    public void setComponent(HashMap<String, JComponent> componentsMap) {

        this.txtNombres = (JTextField) componentsMap.get("txtNombres");
        this.txtApellidos = (JTextField) componentsMap.get("txtApellidos");
        this.txtCelular = (JTextField) componentsMap.get("txtCelular");
        this.txtCodigo = (JTextField) componentsMap.get("txtCodigo");
        this.txtDni = (JTextField) componentsMap.get("txtDni");
        this.txtEdad = (JTextField) componentsMap.get("txtEdad");
        this.cboEstado = (JComboBox) componentsMap.get("cboEstado");
        this.btnRegistrar = (JButton) componentsMap.get("btnRegistrar");
        this.btnConfirmar = (JButton) componentsMap.get("btnConfirmar");
        this.btnEliminar = (JButton) componentsMap.get("btnEliminar");
        this.btnEditar = (JButton) componentsMap.get("btnEditar");
        this.btnConsultar = (JButton) componentsMap.get("btnConsultar");
        this.btnBuscar = (JButton) componentsMap.get("btnBuscar");
    }

    public void setDefaulTable(DefaultTableModel defaulTable){
        this.modelo = defaulTable;
    }


}

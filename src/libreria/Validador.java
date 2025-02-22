package libreria; 

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Validador {

    // Valida el campo de nombres, asegurándose de que no esté vacío y contenga solo letras
    public static String validarNombres(String nombres, JTextField txt) {
        if (nombres == null || nombres.trim().isEmpty()) {
            mostrarError("El campo Nombres no puede estar vacío.", txt);
            throw new IllegalArgumentException("Nombres vacíos");
        }
        if (!nombres.matches("^[a-zA-ZáéíóúÁÉÍÓÚnn ]+$")) {
            mostrarError("El campo Nombres solo debe contener letras.", txt);
            throw new IllegalArgumentException("Formato incorrecto en Nombres");
        }
        return nombres.trim(); // Devuelve el nombre sin espacios innecesarios
    }

    // Valida el campo de apellidos, asegurándose de que no esté vacío y contenga solo letras
    public static String validarApellidos(String apellidos, JTextField txt) {
        if (apellidos == null || apellidos.trim().isEmpty()) {
            mostrarError("El campo Apellidos no puede estar vacío.", txt);
            throw new IllegalArgumentException("Apellidos vacíos");
        }
        if (!apellidos.matches("^[a-zA-ZáéíóúÁÉÍÓÚnn ]+$")) {
            mostrarError("El campo Apellidos solo debe contener letras.", txt);
            throw new IllegalArgumentException("Formato incorrecto en Apellidos");
        }
        return apellidos.trim(); // Devuelve el apellido sin espacios innecesarios
    }

    // Valida el campo de DNI, asegurándose de que no esté vacío y contenga exactamente 8 dígitos
    public static String validarDNI(String dni, JTextField txt) {
        if (dni == null || dni.trim().isEmpty()) {
            mostrarError("El campo DNI no puede estar vacío.", txt);
            throw new IllegalArgumentException("DNI vacío");
        }
        if (!dni.matches("^\\d{8}$")) {
            mostrarError("El DNI debe contener exactamente 8 dígitos.", txt);
            throw new IllegalArgumentException("Formato incorrecto en DNI");
        }
        return dni.trim(); // Devuelve el DNI sin espacios innecesarios
    }

    // Método privado para mostrar un mensaje de error en un cuadro de diálogo
    private static void mostrarError(String mensaje, JTextField txt) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        txt.requestFocus(); // Coloca el foco en el campo de texto que causó el error
    }

    // Valida la edad, asegurándose de que sea un número dentro del rango 5-100
    public static int validarEdad(String edad, JTextField txt) {
        if (edad == null || edad.trim().isEmpty()) {
            mostrarError("El campo Edad no puede estar vacío.", txt);
            throw new IllegalArgumentException("Edad vacía");
        }
        if (!edad.matches("^\\d+$")) {
            mostrarError("El campo Edad solo debe contener números.", txt);
            throw new IllegalArgumentException("Formato incorrecto en Edad");
        }

        int edadNum = Integer.parseInt(edad); // Convierte la edad a un número entero
        if (edadNum < 5 || edadNum > 100) {
            mostrarError("La edad debe estar entre 5 y 100 anos.", txt);
            throw new IllegalArgumentException("Rango incorrecto en Edad");
        }

        return edadNum; // Devuelve la edad validada
    }

    // Valida el número de celular, asegurándose de que contenga exactamente 9 dígitos
    public static int validarCelular(String celular, JTextField txt) {
        if (celular == null || celular.trim().isEmpty()) {
            mostrarError("El campo Celular no puede estar vacío.", txt);
            throw new IllegalArgumentException("Celular vacío");
        }
        if (!celular.matches("^\\d{9}$")) {
            mostrarError("El campo Celular debe contener exactamente 9 dígitos.", txt);
            throw new IllegalArgumentException("Formato incorrecto en Celular");
        }

        return Integer.parseInt(celular); // Convierte el número de celular a entero y lo devuelve
    }

    // Valida el código del curso, asegurándose de que no esté vacío, sea un número positivo y tenga entre 3 y 5 dígitos
    public static int validarCodigoCurso(String codigo, JTextField txt) {
        if (codigo == null || codigo.trim().isEmpty()) {
            mostrarError("El campo Código no puede estar vacío.", txt);
            throw new IllegalArgumentException("Código vacío");
        }
        if (!codigo.matches("^\\d+$")) {
            mostrarError("El Código debe ser un número entero.", txt);
            throw new IllegalArgumentException("Formato incorrecto en Código");
        }

        int codigoNum = Integer.parseInt(codigo); // Convierte el código a número entero

        if (codigoNum <= 0) {
            mostrarError("El Código debe ser un número positivo.", txt);
            throw new IllegalArgumentException("Código inválido");
        }

        if (codigo.length() < 3 || codigo.length() > 5) {
            mostrarError("El Código debe tener entre 3 y 5 dígitos.", txt);
            throw new IllegalArgumentException("Longitud del Código incorrecta");
        }

        return codigoNum; // Devuelve el código del curso validado
    }

    // Valida que la asignatura no esté vacía y contenga solo letras
    public static String validarAsignatura(String asignatura, JTextField txt) {
        if (asignatura == null || asignatura.trim().isEmpty()) {
            mostrarError("El campo Asignatura no puede estar vacío.", txt);
            throw new IllegalArgumentException("Asignatura vacía");
        }
        if (!asignatura.matches("^[a-zA-ZáéíóúÁÉÍÓÚnn ]+$")) {
            mostrarError("El campo Asignatura solo debe contener letras.", txt);
            throw new IllegalArgumentException("Formato incorrecto en Asignatura");
        }
        return asignatura.trim(); // Devuelve la asignatura sin espacios innecesarios
    }

    // Valida el número de horas, asegurándose de que esté entre 1 y 4
    public static int validarHoras(String horas, JTextField txt) {
        if (horas == null || horas.trim().isEmpty()) {
            mostrarError("El campo Horas no puede estar vacío.", txt);
            throw new IllegalArgumentException("Horas vacías");
        }
        if (!horas.matches("^\\d+$")) {
            mostrarError("El campo Horas debe ser un número entero.", txt);
            throw new IllegalArgumentException("Formato incorrecto en Horas");
        }
        int horasNum = Integer.parseInt(horas); // Convierte las horas a número entero
        if (horasNum < 1 || horasNum > 4) {
            mostrarError("Las horas deben estar entre 1 y 4.", txt);
            throw new IllegalArgumentException("Rango incorrecto en Horas");
        }
        return horasNum; // Devuelve el número de horas validado
    }

    // Valida el número de créditos, asegurándose de que esté entre 1 y 3
    public static int validarCreditos(String creditos, JTextField txt) {
        if (creditos == null || creditos.trim().isEmpty()) {
            mostrarError("El campo Créditos no puede estar vacío.", txt);
            throw new IllegalArgumentException("Créditos vacíos");
        }
        if (!creditos.matches("^\\d+$")) {
            mostrarError("El campo Créditos debe ser un número entero.", txt);
            throw new IllegalArgumentException("Formato incorrecto en Créditos");
        }
        int creditosNum = Integer.parseInt(creditos); // Convierte los créditos a número entero
        if (creditosNum < 1 || creditosNum > 3) {
            mostrarError("Los créditos deben estar entre 1 y 3.", txt);
            throw new IllegalArgumentException("Rango incorrecto en Créditos");
        }
        return creditosNum; // Devuelve el número de créditos validado
    }

    // Valida que se haya seleccionado un ciclo válido en el combo box
    public static int validarCiclo(JComboBox<String> cboCiclo) {
        String cicloSeleccionado = (String) cboCiclo.getSelectedItem();

        if (cicloSeleccionado == null || cicloSeleccionado.equals("Seleccionar")) {
            mostrarError("Por favor, seleccione un ciclo válido.", cboCiclo);
            throw new IllegalArgumentException("Ciclo no seleccionado o inválido");
        }

        return cboCiclo.getSelectedIndex(); // Devuelve el índice del ciclo seleccionado
    }

    // Método para mostrar un mensaje de error para el combo box
    public static void mostrarError(String mensaje, JComboBox<String> comboBox) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        comboBox.requestFocus(); // Coloca el foco en el combo box que causó el error
    }
}

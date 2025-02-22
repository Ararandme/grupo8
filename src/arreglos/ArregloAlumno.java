package arreglos;

import clases.Alumno;
import java.io.*;
import java.util.ArrayList;

public class ArregloAlumno {

	private final ArrayList<Alumno> alumno;
	private final String url = "src/doc/alumnos.txt";

	public ArrayList<Alumno> GetAlumnos() {
		return alumno;
	}


	public ArregloAlumno() {
		alumno = new ArrayList<>();
		cargarAlumnos();
	}

	// Método para adicionar un nuevo alumno
	public void adicionar(Alumno x) {
		alumno.add(x);
		grabarAlumno();
	}

	// Método para obtener un alumno por índice
	public Alumno obtener(int i) {
		return alumno.get(i);
	}

	// Método para obtener el tamano del arreglo
	public int tamano() {
		return alumno.size();
	}

	// Método para buscar un alumno por DNI
	public Alumno buscarDNI(String dni) {
		for (Alumno x : alumno) {
			if (x.getDni().equals(dni)) {
				return x;
			}
		}
		return null;
	}

	// Método para buscar un alumno por código
	public Alumno buscar(int codAlumno) {
		for (Alumno x : alumno) {
			if (x.getCodAlumno() == codAlumno) {
				return x;
			}
		}
		return null;
	}

	// Método para eliminar un alumno
	public void eliminar(Alumno x) {
		alumno.remove(x);
		grabarAlumno();
	}

	// Método para cargar alumnos desde el archivo
	private void cargarAlumnos() {
		try (BufferedReader br = new BufferedReader(new FileReader(url))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] s = linea.split(";");
				int codAlumno = Integer.parseInt(s[0].trim());
				String nombre = s[1].trim();
				String apellido = s[2].trim();
				String dni = s[3].trim();
				int edad = Integer.parseInt(s[4].trim());
				int celular = Integer.parseInt(s[5].trim());
				int estado = Integer.parseInt(s[6].trim());

				alumno.add(new Alumno(codAlumno, nombre, apellido, dni, edad, celular, estado));
			}
		} catch (IOException e) {
			System.out.println("Error al cargar el archivo: " + e.getMessage());
		}
	}

	// Método para grabar alumnos en el archivo
	private void grabarAlumno() {
		try (PrintWriter pw = new PrintWriter(new FileWriter(url))) {
			for (Alumno x : alumno) {
				String linea = x.getCodAlumno() + ";" + x.getNombre() + ";" + x.getApellido() + ";" + x.getDni() + ";"
						+ x.getEdad() + ";" + x.getCelular() + ";" + x.getEstado();
				pw.println(linea);
			}
		} catch (IOException e) {
			System.out.println("Error al grabar el archivo: " + e.getMessage());
		}
	}

	// Método para generar un código correlativo
	public int numeroCorrelativo() {
		if (tamano() == 0) {
			return 202500000; // Código inicial
		} else {
			return alumno.get(tamano() - 1).getCodAlumno() + 1;
		}
	}

	// Método para actualizar el archivo (puede ser llamado externamente)
	public void actualizarArchivo() {
		grabarAlumno();
	}

	// Método para actualizar los datos de un alumno
	public void actualizarAlumno(int codAlumno, String nombre, String apellido, String dni, int edad, int celular,
			int estado) {
		Alumno x = buscar(codAlumno);
		if (x != null) {
			x.setNombre(nombre);
			x.setApellido(apellido);
			x.setDni(dni);
			x.setEdad(edad);
			x.setCelular(celular);
			x.setEstado(estado);
			grabarAlumno();
		}
	}

}

package arreglos;

import clases.Curso;
import java.io.*;
import java.util.ArrayList;

public class ArregloCurso {

	private final ArrayList<Curso> curso;
	private final String url = "src/doc/cursos.txt";

	private ArrayList<Curso> listarCursos() {
		return curso;
	}

	public ArregloCurso() {
		curso = new ArrayList<>();
		cargarCursos();
	}

	public void adicionar(Curso x) {
		curso.add(x);
		grabarCurso();
	}

	public Curso obtener(int i) {
		return curso.get(i);
	}

	public int tamano() {
		return curso.size();
	}

	public Curso buscar(int codigoCurso) {
		for (int i = 0; i < tamano(); i++)
			if (obtener(i).getCodCurso() == codigoCurso) {
				return obtener(i);
			}
		return null;
	}

	public Curso buscarPorAsignatura(String asignatura) {
		for (int i = 0; i < tamano(); i++) {
			if (obtener(i).getAsignatura().equalsIgnoreCase(asignatura)) {
				return obtener(i);
			}
		}
		return null;
	}

	public void eliminar(Curso x) {
		curso.remove(x);
		grabarCurso();
	}

	private void cargarCursos() {
		try {
			BufferedReader br;
			String linea;
			String[] s;
			int codCurso;
			String asignatura;
			int ciclo;
			int creditos;
			int hora;

			br = new BufferedReader(new FileReader(url));
			while ((linea = br.readLine()) != null) {
				s = linea.split(";");
				codCurso = Integer.parseInt(s[0].trim());
				asignatura = s[1].trim();
				ciclo = Integer.parseInt(s[2].trim());
				creditos = Integer.parseInt(s[3].trim());
				hora = Integer.parseInt(s[4].trim());

				adicionar(new Curso(codCurso, asignatura, ciclo, creditos, hora));

			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void grabarCurso() {
		try {
			PrintWriter pw;
			String linea;
			Curso x;
			pw = new PrintWriter(new FileWriter(url));
			for (int i = 0; i < tamano(); i++) {
				x = obtener(i);
				linea = x.getCodCurso() + ";" + x.getAsignatura() + ";" + x.getCiclo() + ";" + x.getCreditos() + ";"
						+ x.getHoras();
				pw.println(linea);
			}
			pw.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void actualizarArchivo() {
		grabarCurso();
	}

}

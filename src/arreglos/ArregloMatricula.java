package arreglos;

import java.util.ArrayList;
import java.io.*;

import clases.Matricula;

public class ArregloMatricula {

	private final ArrayList<Matricula> matricula;
	private final String url = "src/doc/matriculas.txt";
	public ArregloMatricula() {
		matricula = new ArrayList<Matricula>();
		cargarMatricula();
	}

	public void adicionar(Matricula x) {
		matricula.add(x);
		grabarMatricula();
	}

	public int tamano() {
		return matricula.size();
	}

	public Matricula obtener(int i) {
		return matricula.get(i);
	}

	public Matricula buscar(int codigoMatricula) {
		for (int i = 0; i < tamano(); i++)
			if (obtener(i).getCodMatricula() == codigoMatricula)
				return obtener(i);
		return null;
	}

	public Matricula buscarCurso(int codigoCurso) {
		for (int i = 0; i < tamano(); i++)
			if (obtener(i).getCodCurso() == codigoCurso)
				return obtener(i);
		return null;
	}

	public Matricula buscarAlumno(int codigoAlumno) {
		for (int i = 0; i < tamano(); i++)
			if (obtener(i).getCodAlumno() == codigoAlumno)
				return obtener(i);
		return null;
	}

	public void eliminar(Matricula x) {
		matricula.remove(x);
		grabarMatricula();
	}

	public void actualizarArchivo() {
		grabarMatricula();
	}

	private void grabarMatricula() {
		try {
			PrintWriter pw;
			String linea;
			Matricula x;
			pw = new PrintWriter(new FileWriter(url));
			for (int i = 0; i < tamano(); i++) {
				x = obtener(i);
				linea = x.getCodMatricula()
						+ ";"
						+ x.getFecha()
						+ ";"
						+ x.getHora()
						+ ";"
						+ x.getCodAlumno()
						+ ";"
						+ x.getCodCurso()
						+ ";"
						+ x.getEstado();
				pw.println(linea);
			}
			pw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarMatricula() {
		try {
			BufferedReader br;
			String hora, fecha, linea;
			String[] s;
			int codMatricula, codAlumno, codCurso, estado;
			br = new BufferedReader(new FileReader(url));
			while ((linea = br.readLine()) != null) {
				s = linea.split(";");
				codMatricula = Integer.parseInt(s[0].trim());
				fecha = s[1].trim();
				hora = s[2].trim();
				codAlumno = Integer.parseInt(s[3].trim());
				codCurso = Integer.parseInt(s[4].trim());
				estado = Integer.parseInt(s[5].trim());
				adicionar(new Matricula(codMatricula, fecha, hora, codAlumno, codCurso, estado));
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int codigoCorrelativo() {
		if (tamano() == 0)
			return 100000;
		else
			return obtener(tamano() - 1).getCodMatricula() + 1;
	}

}

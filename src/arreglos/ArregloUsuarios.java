package arreglos;
import clases.Usuario;
import java.io.*;
import java.util.ArrayList;

public class ArregloUsuarios {

	private final ArrayList<Usuario> listaUsuarios;

	 public ArregloUsuarios() {
	        listaUsuarios = new ArrayList<>();
	        cargarUsuariosDesdeArchivo();
	    }

	private void cargarUsuariosDesdeArchivo() {
        String archivo = "src/doc/usuarios.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    listaUsuarios.add(new Usuario(partes[0], partes[1], partes[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }

	public Usuario validarUsuario(String usuario, String contrasena) {
		for (Usuario u : listaUsuarios) {
			if (u.getUsuario().equals(usuario) && u.getContrasena().equals(contrasena)) {
				return u;
			}
		}
		return null;
	}

}
